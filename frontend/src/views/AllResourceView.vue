<template>
    <div class = "screen">
        <div class="modal_black" v-if="showModal">
            <div class="resource_modal">
                <button style="position: absolute; left: 1px; top: 5px; border: none; background-color: transparent; 
                           color: black; font-size: 20px; font-family: 'SUITE-Regular';" @click="closeModal">X</button>
                <h3>{{ modalResourceInfo.name }}</h3>
                <img :src="modalResourceInfo.src" style="width: 150px; height: 150px;"/> 
                <p><span style="color: #537FE7; font-weight: bold;">{{ modalResourceInfo.auth }}</span> 권한 이상의 사용자만 사용 가능합니다.</p>
                <p v-html="modalResourceInfo.info"></p>
            </div>
        </div>
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px; font-family: 'SUITE-Regular';"> &lt; 전체 리소스</button>
                <button class = "gear" @click="clickGear"></button>
            </div>
            <div :class="{'hidden_drawer': gearClick}">
                <ul class="drawer"
                    style="position: absolute; top: 0px; right: 30px; 
                           background-color: white; width: 100px; height: 100px; border-radius: 20px;">
                    <li class="drawer_list" v-bind:style="isNotSorted" style="border-bottom: 1px solid #808080;" @click="sortAll"> 전체 </li>
                    <li class="drawer_list" v-bind:style="isSortedMine" style="border-bottom: 1px solid #808080;" @click="sortMine"> 권한 내 리소스 </li>
                    <li class="drawer_list" v-bind:style="isSortedForbidden" @click="sortForbidden"> 권한 외 리소스 </li>
                </ul>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: 'SUITE-Regular';">All Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px; font-family: 'SUITE-Regular';">모든 리소스 목록입니다.<br>
                정렬을 통해 권한 별 리소스 확인이 가능합니다.</p>
        </div>
        <div style="width: 389px; height: 611px; background-color: white; overflow: scroll;">
            <ul>
                <template :key="resource.deviceName" v-for="resource in listForShow">
                    <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;">
                        <img v-if="resource.isOn==''" src="../assets/greenglow.png" style="width: 25px; height: 25px;">
                        <img v-if="resource.isOn!=''" src="../assets/redglow.png" style="width: 25px; height: 25px;">
                        <span style="font-size: 18px; line-height: 35px;" @click="openModal(resource.deviceName, resource.auth)">{{ resource.deviceName }}</span>
                        <button v-if="resource.auth == 'Student'" class="resource_button_ask" @click="requestAdd(resource.id)">추가</button>
                        <button v-if="resource.auth == 'Professor'" class="resource_button_ask" style="background-color: #3ADF00;" @click="requestPermission(resource.id)">요청</button>
                    </li>
                </template>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref, onMounted, inject} from 'vue'
import axios from 'axios';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')

const gearClick = ref(true)
const listForShow = ref([
])
const sortedMyResourceList = ref([])
const sortedForbiddenResourceList = ref([])
const isNotSorted = ref({color: 'red'});
const isSortedMine = ref();
const isSortedForbidden = ref();

const modalResourceInfo = ref({
    name: "",
    auth: "",
    src: "",
    info: ""
})
const resourceInfos = new Map([
    [ "자판기", '제도관 2층에 위치해 있습니다.<br>버튼을 통해 원하는 물건을 뽑을 수 있습니다!' ],
    [ "도어락(6210)", '제도관 2층에 위치해 있습니다.<br>김XX 교수님의 연구실입니다.' ],
    [ "선풍기", "제도관 2층에 위치해 있습니다.<br>한여름 더위를 시원하게 날려버리세요!" ],
    [ "전등", "제도관 2층에 위치해 있습니다.<br>어두워서 잘 안보이시나요? 불을 켜드릴게요!"]
])
const showModal = ref(false)
const resourceList = ref([])
const addRequestDto = ref({
    resourceId: 0
})

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestResourceList();
})

function requestResourceList(){ // 기본적으로 전체 정렬
    axios.get(requestURL + "resources", {headers})
        .then((resp) => {
            resourceList.value = resp.data;
            listForShow.value = resourceList.value;
        })
}

// 여기서 부터 정렬 메서드
function sortAll(){
    isNotSorted.value = {color: "red"};
    isSortedMine.value = {color: "black"};
    isSortedForbidden.value = {color: "black"};

    listForShow.value = resourceList.value
}
function sortMine(){
    isNotSorted.value = {color: "black"};
    isSortedMine.value = {color: "red"};
    isSortedForbidden.value = {color: "black"};

    resourceList.value.forEach(object => {
        if(object['auth'] === "Student")
            sortedMyResourceList.value.push(object)
        // 추후에 권한을 현재 로그인 한 사람 권한으로 바꿔야함
        // 방식은 store.state.authority를 통해서!
    });

    listForShow.value = sortedMyResourceList.value
    sortedMyResourceList.value = []
}
function sortForbidden(){
    isNotSorted.value = {color: "black"};
    isSortedMine.value = {color: "black"};
    isSortedForbidden.value = {color: "red"};

    resourceList.value.forEach(object => {
        if(object['auth'] === "Professor")
            sortedForbiddenResourceList.value.push(object)
    });
    
    listForShow.value = sortedForbiddenResourceList.value
    sortedForbiddenResourceList.value = []
}
// 정렬 버튼 나오게
function clickGear(){
    gearClick.value = !gearClick.value
}

// 이전 화면으로
function moveToPrev(){
    router.go(-1)
}

// 여기서부터는 요청 메서드
function requestAdd(target){
    addRequestDto.value.resourceId = target // 리소스 아이디 받아옴
    axios.post(requestURL + "addResource", addRequestDto.value, {headers})
         .then((resp) => {
            if(resp.status === 200){
                alert("리소스가 추가 되었습니다.")
                router.go(0)
            }
         })
         .catch((error) => {
            alert(error)
         })

    // 해당 요청에 들어갈 값은
    // 버튼을 눌렀을 때 해당 리소스 이름
}


function requestPermission(target){
    addRequestDto.value.resourceId = target // 리소스 아이디 받아옴
    // 권한 밖의 요청
    axios.post(requestURL + "askResource", addRequestDto.value, {headers})
         .then((resp) => {
            if(resp.status === 200){
                alert("리소스 추가 요청이 완료되었습니다.")
                router.go(0)
            }
         })
         .catch((error) => {
            alert(error)
         })

    // 해당 요청에 들어갈 값은
    // 버튼을 눌렀을 때 해당 리소스 이름
}

// 여기서부터는 모달 창 관련 메서드 
function openModal(targetName, targetAuth){
    showModal.value = true
    modalResourceInfo.value.name = targetName
    if(targetAuth == "Student")
        modalResourceInfo.value.auth = "학생"
    else
        modalResourceInfo.value.auth = "교수"
    modalResourceInfo.value.src = require("../assets/" + targetName + ".png")
    modalResourceInfo.value.info = resourceInfos.get(targetName)
} 

function closeModal(){
    showModal.value = false
}
</script>

<style>
@import "../style/wmistyle.css";
</style>