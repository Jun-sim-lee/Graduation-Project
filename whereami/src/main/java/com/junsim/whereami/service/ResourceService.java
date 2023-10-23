package com.junsim.whereami.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junsim.whereami.config.MqttGateway;
import com.junsim.whereami.domain.*;
import com.junsim.whereami.dto.*;
import com.junsim.whereami.errors.exception.Exception400;
import com.junsim.whereami.repository.AskRepository;
import com.junsim.whereami.repository.IntersectionRepository;
import com.junsim.whereami.repository.MemberRepository;
import com.junsim.whereami.repository.ResourceRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.junsim.whereami.utility.SecurityUtility.currentMember;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final MemberRepository memberRepository;
    private final IntersectionRepository intersectionRepository;
    private final AskRepository askRepository;
    private final ObjectMapper om;
    private final MqttGateway mqttGateway;

    public void registerResource(ResourceRequestDTO resourceRequestDTO) {
        Authority authority = Authority.Admin;
        if (resourceRequestDTO.getAuth().equals("학생"))
            authority = Authority.Student;
        else if (resourceRequestDTO.getAuth().equals("교수"))
            authority = Authority.Professor;
        resourceRepository.save(new Resource(resourceRequestDTO.getDeviceName(),authority,
                new Location(resourceRequestDTO.getX(), resourceRequestDTO.getY())));
    }

    public List<AllResourceResponseDTO> findAllResource() {
        return resourceRepository.findAll()
                .stream()
                .map(AllResourceResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void changeResource(ChangeResourceRequestDTO changeResourceRequestDTO) {
        Resource resource = resourceRepository.findById(changeResourceRequestDTO.getId()).get();
        resource.changeInfo(changeResourceRequestDTO);
    }

    public void addResource(AddResourceRequestDTO addResourceRequestDTO) {
        Member member = memberRepository.findByEmail(currentMember()).get();
        Resource resource = resourceRepository.findById(addResourceRequestDTO.getResourceId()).get();
        MemberResourceInterSection memberResourceInterSection = new MemberResourceInterSection(member, resource);
        intersectionRepository.save(memberResourceInterSection);
        member.addResource(memberResourceInterSection);
        resource.addMember(memberResourceInterSection);
    }

    public void askResource(AskResourceRequestDTO askResourceRequestDTO) {
        Optional<Member> member = memberRepository.findByEmail(currentMember());
        Optional<Resource> resource = resourceRepository.findById(askResourceRequestDTO.getResourceId());
        Ask ask = new Ask(member.get(), resource.get());
        askRepository.save(ask);
        member.get().addAsk(ask);
    }

    public List<MyResourceResponseDTO> findMyResource() {
        return memberRepository.findByEmail(currentMember()).get()
                .getMyResource()
                .stream()
                .map(MemberResourceInterSection::getResource)
                .map(MyResourceResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<AllAskResponseDTO> findAllAsk() {
        return askRepository.findAllAsk()
                .stream()
                .map(AllAskResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void acceptAsk(AcceptAskRequestDTO acceptAskRequestDTO) {
        Member member = memberRepository.findByEmail(acceptAskRequestDTO.getEmail()).get();
        Resource resource = resourceRepository.findById(acceptAskRequestDTO.getDeviceId()).get();
        MemberResourceInterSection memberResourceInterSection = new MemberResourceInterSection(member,resource);
        intersectionRepository.save(memberResourceInterSection);
        member.addResource(memberResourceInterSection);
        resource.addMember(memberResourceInterSection);
        askRepository.delete(askRepository.findById(acceptAskRequestDTO.getAskId()).get());
    }

    public void rejectAsk(Long id) {
        askRepository.delete(askRepository.findById(id).get());
    }

    public void deleteMyResource(Long id) {
        MemberResourceInterSection interSection = intersectionRepository.findByMemberIDAndResourceId(currentMember(), id).get();
        Member member = memberRepository.findByEmail(currentMember()).get();
        Resource resource = resourceRepository.findById(id).get();
        member.getMyResource().remove(interSection);
        resource.getMemberList().remove(interSection);
        intersectionRepository.delete(interSection);
    }

    public void nearResource(Member member) throws JsonProcessingException {
        Location memberLocation = member.getLocation();
        for(MemberResourceInterSection resourceInterSection : member.getMyResource()) {
            Resource resource = resourceInterSection.getResource();
            if (calcDistance(memberLocation, resource.getLocation()) <= 1 && resource.getIsOn().length() == 0){
                resourceOn(member, resource);
            }
            if (calcDistance(memberLocation, resource.getLocation()) > 1 && resource.getIsOn().equals(member.getEmail())){
                resourceOff(resource);
            }
        }
    }

    private void resourceOff(Resource resource) {
        mqttGateway.sendToMqtt("stop!", "rpi/" + resource.getId());
        resource.turnOff();
    }

    public void resourceOn(Member member, Resource resource) throws JsonProcessingException {
        PushMessageRequestDTO pushMessageRequestDTO =
                new PushMessageRequestDTO(member.getToken(),
                        new Notification(resource.getDeviceName(), resource.getId().toString()));
        String request = om.writeValueAsString(pushMessageRequestDTO);
        System.out.println("request = " + request);
        WebClient webClient = WebClient.builder()
                .baseUrl("https://fcm.googleapis.com/fcm/send")
                .build();
        webClient.post()
                .header("Authorization","key=AAAAjNmXltY:APA91bHaAvqkkxaR3vyzA1XQhKJXwBhlwQNkR" +
                        "Q4s0m_aiETvzJLcccq5tYMuEQ_W4LQNHpJNBhj3VuIzd7KtB2o7J0Pk02zye_hIsT-wiUxvk3osg1s9QlJJe7o_zbBR8od" +
                        "6I8xYdFr-")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(responseBody -> {
                    System.out.println("Response: " + responseBody);
                });
    }

    public void useResource(Long id, HttpServletRequest request) {
        Resource resource = resourceRepository.findById(id).get();
        if(resource.getIsOn().length() != 0)
            throw new Exception400("이미 사용중인 리소스입니다.");
        log.info(request.getHeader("Authorization"));
        mqttGateway.sendToMqtt("work!", "rpi/" + id);
        resource.turnOn(currentMember());
    }

    public Integer calcDistance(Location member, Location resource) {
        return Math.abs(member.getX() - resource.getX()) + Math.abs(member.getY() - resource.getY());
    }

}
