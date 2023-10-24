<template>
    <div class="modal_black" v-if="showPathModal">
        <div class="modal_window" style="height: 120px;">
            <p>해당 위치로의 최단 경로를<br>안내 받으시겠습니까?</p>
            <button class="modal_button" style="background-color: #537FE7;" @click="requestWithPathModal">예</button>
            &nbsp;
            <button class = "modal_button" style="background-color: red;" @click="closePathModal">아니오</button>
        </div>
    </div>
    <div class="modal_black" v-if="showModal">
        <div class="modal_window">
            <h3>{{targetResource}}를(을) 사용 하시겠습니까?</h3>
            <p>{{ targetResource }}(컴퓨터공학관 2층)</p>
            <button class="modal_button" style="background-color: #537FE7;" @click="closeModalWithRequest">예</button>
            &nbsp;
            <button class = "modal_button" style="background-color: red;" @click="closeModal">아니오</button>
        </div>
    </div>
    <div class="modal_black" v-if="showEntireMapModal">
        <div class="entire_map_modal">
            <div v-bind:style="{position: 'absolute', zIndex: 3, left: `${computedXforEntMap}px`, top: `${computedYforEntMap}px`, 
                                backgroundColor: 'red', pointerEvents: 'none',
                                border: '2px solid aliceblue', borderRadius: '100px', width: '5px', height: '5px'}"
                     id = "client_dot"></div>
            <button style="position: absolute; left: 2px; top: 5px; border: none; background-color: transparent; 
                           color: black; font-size: 20px; font-family: 'SUITE-Regular';" @click="closeEntireMap">X</button>
        </div>
    </div>
    <div class="screen" @touchend="stopDrag($event)">
        <div class="map_header">
            <span class="triangle_button" @click="backToMain" style="font-family: 'SUITE-Regular';"> &lt; </span>
            <button v-if="!isDest" @click="toSelectDestination" class="map_header_button">목적지를 선택해주세요!</button>
            <button v-if="isDest" @click="cancelShortcut" class="cancel_button">취소</button>
            <div style="width: 30px;"></div>
        </div>
        <div class="map_screen">
            <button class="entire_map_modal_open" @click="showEntMap"></button>
            <div @touchstart="startDrag($event)" @touchmove="moveDrag($event)" class="map_div">
                <div v-bind:style="{position: 'absolute', zIndex: 3, left: `${computedX}px`, top: `${computedY}px`, 
                                backgroundColor: 'red', pointerEvents: 'none',
                                border: '5px solid aliceblue', borderRadius: '100%', width: '10px', height: '10px'}"
                     id = "client_dot"></div>
                <div id="07" class="path_dot" style="left: 900px; top: 350px;" @click="selectDestinationOnMap($event)"></div>
                <div id="08" class="path_dot" style="left: 900px; top: 310px;" @click="selectDestinationOnMap($event)"></div>
                <div id="17" class="path_dot" style="left: 855px; top: 350px;" @click="selectDestinationOnMap($event)"></div>
                <div id="18" class="path_dot" style="left: 855px; top: 310px;" @click="selectDestinationOnMap($event)"></div>
                <div id="27" class="path_dot" style="left: 810px; top: 350px;" @click="selectDestinationOnMap($event)"></div>
                <div id="28" class="path_dot" style="left: 810px; top: 310px;" @click="selectDestinationOnMap($event)"></div>
                <div id="38" class="path_dot" style="left: 765px; top: 310px;" @click="selectDestinationOnMap($event)"></div>
                <div id="37" class="path_dot" style="left: 765px; top: 350px;" @click="selectDestinationOnMap($event)"></div>
                <div id="36" class="path_dot" style="left: 765px; top: 390px;" @click="selectDestinationOnMap($event)"></div>
                <div id="35" class="path_dot" style="left: 765px; top: 430px;" @click="selectDestinationOnMap($event)"></div>
                <div id="34" class="path_dot" style="left: 765px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="44" class="path_dot" style="left: 720px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="54" class="path_dot" style="left: 675px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="64" class="path_dot" style="left: 630px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="74" class="path_dot" style="left: 585px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="84" class="path_dot" style="left: 540px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="94" class="path_dot" style="left: 495px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="104" class="path_dot" style="left: 450px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="114" class="path_dot" style="left: 405px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="124" class="path_dot" style="left: 360px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="134" class="path_dot" style="left: 315px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="144" class="path_dot" style="left: 270px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="154" class="path_dot" style="left: 225px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="164" class="path_dot" style="left: 180px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="174" class="path_dot" style="left: 135px; top: 470px;" @click="selectDestinationOnMap($event)"></div>
                <div id="39" class="path_dot" style="left: 765px; top: 270px;" @click="selectDestinationOnMap($event)"></div>
                <div id="310" class="path_dot" style="left: 765px; top: 230px;" @click="selectDestinationOnMap($event)"></div>
                <div id="311" class="path_dot" style="left: 765px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="411" class="path_dot" style="left: 720px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="511" class="path_dot" style="left: 675px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="611" class="path_dot" style="left: 630px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="711" class="path_dot" style="left: 585px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="811" class="path_dot" style="left: 540px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="911" class="path_dot" style="left: 495px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="1011" class="path_dot" style="left: 450px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="1111" class="path_dot" style="left: 405px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="1211" class="path_dot" style="left: 360px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="1311" class="path_dot" style="left: 315px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="1411" class="path_dot" style="left: 270px; top: 190px;" @click="selectDestinationOnMap($event)"></div>
                <div id="1410" class="path_dot" style="left: 270px; top: 230px;" @click="selectDestinationOnMap($event)"></div>
                <div id="149" class="path_dot" style="left: 270px; top: 270px;" @click="selectDestinationOnMap($event)"></div>
                <div id="148" class="path_dot" style="left: 270px; top: 310px;" @click="selectDestinationOnMap($event)"></div>
                <div id="147" class="path_dot" style="left: 270px; top: 350px;" @click="selectDestinationOnMap($event)"></div>
                <div id="146" class="path_dot" style="left: 270px; top: 390px;" @click="selectDestinationOnMap($event)"></div>
                <div id="145" class="path_dot" style="left: 270px; top: 430px;" @click="selectDestinationOnMap($event)"></div>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref, computed, onMounted, onUnmounted, inject} from 'vue'
import { router } from '@/router';
import {initializeApp} from "firebase/app"
import {getMessaging, getToken, onMessage} from "firebase/messaging"
import axios from 'axios';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
const isDest = ref(false)

// 여기부터 지도 관련 JS
var imgLeft = 0
var imgTop = 0
var moveLeft = 0
var moveTop = 0
var timer;

const showModal = ref(false)
const showPathModal = ref(false)
const showEntireMapModal = ref(false)
const isClick = ref(false)
const coordDto = ref({
    x: 0,
    y: 8

    // 0, 8 : 900, 310 // 0, 7 : 900, 350
    // 1, 8 : 850, 310 // 1, 7 : 850, 350
    // 2, 8 : 800, 310 // 3, 6 : 750, 390
    // 3, 8 : 750, 300 // 3, 5 : 750, 430 // 3, 4 : 750, 470 => 510 => 550 => 590 => 630
})
const shortestPath = ref([])
const allPath = ref([
    "08", "18", "28", "38", "07", "17", "27", "37", "39", "310", "311", "36", "35", "34",
    "44", "54", "64", "74", "84", "94", "104", "114", "124", "134", "144", "154", "164", "174",
    "411", "511", "611", "711", "811", "911", "1011", "1111", "1211", "1311", "1411",
    "1410", "149", "148", "147", "146", "145"
])
const splittedX = ref()
const splittedY = ref()

const computedX = computed(() => 900 - (coordDto.value.x) * 45);
const computedY = computed(() => 630 - (coordDto.value.y) * 40);
const computedXforEntMap = computed(() => 13 + (coordDto.value.y) * 24); // 24씩 움직여보자
const computedYforEntMap = computed(() => 520 - (coordDto.value.x) * 27); // 27씩 움직이자 (5)

// 0, 8 : 205, 520 // 0, 7 : 181, 520
// 1, 8 : 205, 493 // 1, 7 : 181, 493
// 2, 8 : 205, 466 // 3, 6 : 157, 439
// 3, 8 : 205, 439 // 3, 5 : 133, 439 // 3, 4 : 109, 439 => 85 => 61 => 37 => 13
// 3, 11 : 277, 439 // 4, 11 : 277, 412
onMounted(() => {
    timer = setInterval(getCoordination, 500);
})

onUnmounted(() => {
    clearInterval(timer);
})

function getCoordination(){
    if(localStorage.getItem("isDest")){
        isDest.value = true
        getShortcut()
    }

    axios.get(requestURL + "myLocation", {headers})
        .then((resp) => {
            coordDto.value.x = resp.data.x;
            coordDto.value.y = resp.data.y;
        })
        .catch((err) => {
            console.log(err)
    })

    if(localStorage.getItem("x") == coordDto.value.x && localStorage.getItem("y") == coordDto.value.y)
        cancelShortcut()
    // 목적지에 도달하면 자동으로 종료
}

function getShortcut(){
    eraseShortcut()

    axios.get(requestURL + "findPath?x=" + localStorage.getItem("x") + "&y=" + localStorage.getItem("y"), {headers})
            .then((resp) => {
                shortestPath.value = resp.data.shortestPath
            })

    drawShortcut()
}

function cancelShortcut(){
    isDest.value = false

    localStorage.removeItem("isDest")
    localStorage.removeItem("x")
    localStorage.removeItem("y")

    eraseShortcut()
    router.go(0)
}

function eraseShortcut(){
    allPath.value.forEach(element => {
        document.getElementById(element).style.backgroundColor = 'transparent'
        document.getElementById(element).style.border = '20px solid transparent'
    })
}

function drawShortcut(){
    shortestPath.value.forEach(Object => {
        if(Object.x.toString() == localStorage.getItem("x") && Object.y.toString() == localStorage.getItem("y")){
            document.getElementById(Object.x.toString() + Object.y.toString()).style.backgroundColor = 'blue'
            document.getElementById(Object.x.toString() + Object.y.toString()).style.border = '5px solid aliceblue'
        }
        else{
            document.getElementById(Object.x.toString() + Object.y.toString()).style.backgroundColor = 'black'
            document.getElementById(Object.x.toString() + Object.y.toString()).style.border = 'none'
        }
    })
}

// 사진 옮기는 로직
function startDrag(event){
    if(event.target.id != "")
        return

    imgLeft = event.pageX
    imgTop = event.pageY
    isClick.value = true

    if(event.preventDefault)
        event.preventDefault();
}

function moveDrag(event){
    if(event.target.id != ""){
        return true
    }
    
    if(isClick.value){
        moveLeft = event.pageX
        moveTop = event.pageY
        if(parseInt(event.target.style.left.replace('px', '')) >= -550 && parseInt(event.target.style.left.replace('px', '')) <= 0)
            event.target.style.left = (parseInt(event.target.style.left.replace('px', '')) - ((imgLeft - moveLeft))/15) + "px";
        else if(parseInt(event.target.style.left.replace('px', '')) < -550)
            event.target.style.left = -549 + "px";
        else
            event.target.style.left = 0 + "px"; // 여기가 좌우 조절하는 로직
        
        if(parseInt(event.target.style.top.replace('px', '')) >= -10 && parseInt(event.target.style.top.replace('px', '')) <= 0)
            event.target.style.top = (parseInt(event.target.style.top.replace('px', '')) - ((imgTop - moveTop))/15) + "px";
        else if(parseInt(event.target.style.top.replace('px', '')) < -10)
            event.target.style.top =  -9 + "px";
        else
            event.target.style.top = 0 + "px"; // 여기가 상하 조절하는 로직

        return false
    }
}

function stopDrag(){
    isClick.value = false

    imgLeft = moveLeft
    imgTop = moveTop
}

function backToMain(){
    router.go(-1)
}

// Firebase Configuration JS, 여기부터 푸시 알림 관련 함수
const firebaseConfig = {
  apiKey: "AIzaSyCLra9MeXh-98x5szVBu1cj5VPPYatvKPQ",
  authDomain: "wmi-project-d857c.firebaseapp.com",
  projectId: "wmi-project-d857c",
  storageBucket: "wmi-project-d857c.appspot.com",
  messagingSenderId: "604946011862",
  appId: "1:604946011862:web:2e7b9b29d109248e6ddb77",
  measurementId: "G-C2WWDN9JND"
};

const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);
const fcmTokenDto = ref({
    token: ""
})
const targetResource = ref("");
const resourceId = ref(0);

getToken(messaging, {vapidKey: 'BNV2fY1k14mbXewu0_IVLr4IsozxZzfAnovQUK4SEqDpoGKGIWE3vQna-qamssoE4Dbl408i3erU5r4Bx8s-T_o'})
    .then((currentToken) => {
        if(currentToken){
            fcmTokenDto.value.token = currentToken
            axios.post(requestURL + "setToken", fcmTokenDto.value ,{headers})
                .catch((error) => {
                    alert(error)
                })
            // 위와 같이 서버에 유저 토큰을 전송해야 푸시 알림을 받을 수 있다.
        }
        else{
            alert("There's no registration token available")
        }
    }).catch((err) => {
        alert(err);
    })

onMessage(messaging, (payload) => {
    targetResource.value = payload.notification.title;
    resourceId.value = payload.notification.body;
    
    showModal.value = true;
})

// 여기부터는 모달 창 관리

function closeModalWithRequest(){
    showModal.value = false;
    axios.post(requestURL + "turnOn/" + resourceId.value, '',{headers})
        .then()
        .catch((error) => {
            if(error.response.data.error.message === "이미 사용중인 리소스입니다.")
                alert("이미 사용 중인 리소스입니다.")
        })
}

function closeModal(){
    showModal.value = false;
}

function showEntMap(){
    showEntireMapModal.value = true;
}

function closeEntireMap(){
    showEntireMapModal.value = false;
}

// 목적지 설정 Router
function toSelectDestination(){
    router.push('destination')
}

function selectDestinationOnMap(event){
    if(isDest.value === true)
        return

    showPathModal.value = true
    var targetId = event.target.id
    
    if(targetId.length == 2){
        splittedX.value = targetId[0]
        splittedY.value = targetId[1]
    }
    if(targetId.length == 3){
        if(targetId[0] == 1){
            splittedX.value = parseInt(targetId[0] + targetId[1])
            splittedY.value = parseInt(targetId[2])
        }
        else{
            splittedX.value = parseInt(targetId[0])
            splittedY.value = parseInt(targetId[1] + targetId[2])
        }
    }
    if(targetId.length == 4){
        splittedX.value = parseInt(targetId[0] + targetId[1])
        splittedY.value = parseInt(targetId[2] + targetId[3])
    }
}

function requestWithPathModal(){
    localStorage.setItem("x", splittedX.value)
    localStorage.setItem("y", splittedY.value)
    localStorage.setItem("isDest", true)

    showPathModal.value = false
}

function closePathModal(){
    showPathModal.value = false
}
</script>

<style>
@import "../style/wmistyle.css";
</style>