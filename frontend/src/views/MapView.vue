<template>
    <div class="modal_black" v-if="showModal">
        <div class="modal_window">
            <h3>{{targetResource}}를(을) 사용 하시겠습니까?</h3>
            <p>{{ pushMessage }}</p>
            <button class="modal_button" style="background-color: #537FE7;" @click="closeModalWithRequest">예</button>
            &nbsp;
            <button class = "modal_button" style="background-color: red;" @click="closeModal">아니오</button>
        </div>
    </div>
    <div class="screen" @mouseup="stopDrag($event)">
        <button @click="toSelectDestionation" style="position: absolute; z-index: 3; top: 20px; opacity: 50%; color: black;
                       width: 270px; height: 40px; border-radius: 5px; border: none;">목적지를 선택해주세요!</button>
        <div class="map_screen">
            <div @mousedown="startDrag($event)" @mousemove="moveDrag($event)"
                 class="map_div">
                <div v-bind:style="{position: 'absolute', zIndex: 2, left: `${computedX}px`, top: `${computedY}px`, 
                                backgroundColor: 'red', pointerEvents: 'none',
                                border: '5px solid aliceblue', borderRadius: '100%', width: '10px', height: '10px'}"
                     id = "client_dot"></div>
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
import { useStore } from 'vuex';

const store = useStore();
const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
const requestURL = inject('requestURL')

// 여기부터 지도 관련 JS
var imgLeft = 0
var imgTop = 0
var moveLeft = 0
var moveTop = 0

const isClick = ref(false)
const coordDto = ref({
    client_x: 0,
    client_y: 0
    // 1320에 510이 0, 7과 같다.
    // 1320, 450이 0, 8이니 y축으로는 한 칸에 60 정도
    // 1240, 450이 1, 8이니 x축으로는 한 칸에 80 정도
    // 이제, 받아오는 좌표에 따라 점화식을 만들어 계산할 수 있어야 함
    // x축 기저 : 1320, y축 기저 : 930, 둘 다 - 방향으로 진행
})

const computedX = computed(() => 1320 - (coordDto.value.client_x) * 67);
const computedY = computed(() => 930 - (coordDto.value.client_y) * 60);

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
        if(parseInt(event.target.style.left.replace('px', '')) >= -1000 && parseInt(event.target.style.left.replace('px', '')) <= 0)
            event.target.style.left = (parseInt(event.target.style.left.replace('px', '')) - ((imgLeft - moveLeft))/15) + "px";
        else if(parseInt(event.target.style.left.replace('px', '')) < -1000)
            event.target.style.left = -999 + "px";
        else
            event.target.style.left = 0+ "px";
        if(parseInt(event.target.style.top.replace('px', '')) >= -200 && parseInt(event.target.style.top.replace('px', '')) <= 0)
            event.target.style.top = (parseInt(event.target.style.top.replace('px', '')) - ((imgTop - moveTop))/15) + "px";
        else if(parseInt(event.target.style.top.replace('px', '')) < -200)
            event.target.style.top =  -199 + "px";
        else
            event.target.style.top = 0 + "px";

        return false
    }
}

function stopDrag(){
    isClick.value = false

    imgLeft = moveLeft
    imgTop = moveTop
}

// Firebase Configuration JS
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
const showModal = ref(false);
const targetResource = ref("");
const pushMessage = ref("");
var timer;

getToken(messaging, {vapidKey: 'BNV2fY1k14mbXewu0_IVLr4IsozxZzfAnovQUK4SEqDpoGKGIWE3vQna-qamssoE4Dbl408i3erU5r4Bx8s-T_o'})
    .then((currentToken) => {
        if(currentToken){
            // axios.post(requestURL + "fcmToken", currentToken, {headers})
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
    pushMessage.value = payload.notification.body;

    showModal.value = true;
})

onMounted(() => {
    timer = setInterval(getCoordination, 1000);
})

onUnmounted(() => {
    clearInterval(timer);
})

function getCoordination(){
    axios.get(requestURL + "/map", {headers})
        .then((resp) => {
            coordDto.value.client_x = resp.data.x;
            coordDto.value.client_y = resp.data.y;
        })
        .catch((err) => {
            console.log(err)
        })
}

function closeModalWithRequest(){
    showModal.value = false;
    // 예 버튼을 눌렀을 때 Axios 요청 코드
}

function closeModal(){
    showModal.value = false;
}

// 목적지 설정 Router
function toSelectDestionation(){
    router.push('destination')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>