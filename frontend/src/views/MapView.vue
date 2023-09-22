<template>
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
                           color: black; font-size: 20px;" @click="closeEntireMap">X</button>
        </div>
    </div>
    <div class="screen" @mouseup="stopDrag($event)">
        <div class="map_header">
            <span class="triangle_button" @click="backToMain"> &lt; </span>
            <button v-if="!isDest" @click="toSelectDestionation" class="map_header_button">목적지를 선택해주세요!</button>
            <button v-if="isDest" @click="cancelShortcut" class="cancel_button">취소</button>
            <div style="width: 30px;"></div>
        </div>
        <div class="map_screen">
            <button class="entire_map_modal_open" @click="showEntMap">+</button>
            <div @mousedown="startDrag($event)" @mousemove="moveDrag($event)" class="map_div">
                <div v-bind:style="{position: 'absolute', zIndex: 3, left: `${computedX}px`, top: `${computedY}px`, 
                                backgroundColor: 'red', pointerEvents: 'none',
                                border: '5px solid aliceblue', borderRadius: '100%', width: '10px', height: '10px'}"
                     id = "client_dot"></div>
                <div id="07" style="position: absolute; z-index: 3; width: 15px; height: 15px; border-radius: 100%; background-color: black;
                            left: 900px; top: 350px;" @click="selectDestinationOnMap($event)"></div>
                <div id="08" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 900px; top: 310px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="17" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 855px; top: 350px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="18" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 855px; top: 310px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="27" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 810px; top: 350px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="28" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 810px; top: 310px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="38" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 310px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="37" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 350px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="36" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 390px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="35" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 430px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="34" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="44" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 720px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="54" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 675px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="64" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 630px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="74" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 585px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="84" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 540px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="94" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 495px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="104" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 450px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="114" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 405px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="124" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 360px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="134" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 315px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="144" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="154" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 225px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="164" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 180px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="174" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 135px; top: 470px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="39" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 270px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="310" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 230px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="311" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 765px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="411" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 720px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="511" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 675px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="611" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 630px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="711" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 585px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="811" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 540px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="911" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 495px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="1011" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 450px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="1111" style="position: absolute; z-index: 3; width: 15px; height: 15px; border-radius: 100%; background-color: black;
                            left: 405px; top: 190px;" @click="selectDestinationOnMap"></div>
                <div id="1211" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 360px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="1311" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 315px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="1411" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 190px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="1410" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 230px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="149" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 270px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="148" style="position: absolute; z-index: 3; width: 15px; height: 15px; border-radius: 100%; background-color: black;
                            left: 270px; top: 310px;" @click="selectDestinationOnMap"></div>
                <div id="147" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 350px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="146" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 390px; visibility: hidden;" @click="selectDestinationOnMap"></div>
                <div id="145" style="position: absolute; z-index: 3; width: 10px; height: 10px; border-radius: 100%; background-color: black;
                            left: 270px; top: 430px; visibility: hidden;" @click="selectDestinationOnMap"></div>
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
const showEntireMapModal = ref(false)
const isClick = ref(false)
const coordDto = ref({
    x: 17,
    y: 4

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
    timer = setInterval(getCoordination, 1000000);
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
        document.getElementById(element).style.visibility = 'hidden'
    })
}

function drawShortcut(){
    shortestPath.value.forEach(Object => {
        if(Object.x.toString() == localStorage.getItem("x") && Object.y.toString() == localStorage.getItem("y")){
            document.getElementById(Object.x.toString() + Object.y.toString()).style.backgroundColor = 'blue'
            document.getElementById(Object.x.toString() + Object.y.toString()).style.border = '5px solid aliceblue'
            document.getElementById(Object.x.toString() + Object.y.toString()).style.visibility = 'visible'
        }
        else
            document.getElementById(Object.x.toString() + Object.y.toString()).style.visibility = 'visible'
    })
}

// 사진 옮기는 로직
function startDrag(event){
    imgLeft = event.pageX
    imgTop = event.pageY
    isClick.value = true

    if(event.preventDefault)
        event.preventDefault();
}

function moveDrag(event){
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
        .catch((error) => {
            alert(error)
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
function toSelectDestionation(){
    router.push('destination')
}

function selectDestinationOnMap(event){
    var targetId = event.target.id

    if(targetId.length == 2)
        console.log(targetId[0] + "," + targetId[1])
    if(targetId.length == 3){
        if(targetId[0] == 1){
            console.log(targetId[0] + targetId[1] + "," + targetId[2])
        }
        else{
            console.log(targetId[0] + "," + targetId[1] + targetId[2])
        }
    }
    if(targetId.length == 4)
        console.log(targetId[0] + targetId[1] + "," + targetId[2] + targetId[3])

    //localStorage.setItem("x", splittedX)
    //localStorage.setItem("y", splittedY)
    //localStorage.setItem("isDest", true)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>