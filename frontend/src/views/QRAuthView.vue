<template>
    <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <h1 style="position: absolute; top: 330px; font-size: 20px">QR 인증입니다.</h1>
            <h1 style="position: absolute; top: 355px; font-size: 20px">각 자리에 해당하는 문자를 입력해 주십시오.</h1>
            <div class = "qr_wrapper">
                <div class = "qr">{{ qrInfo.pos1 }}</div>
                <div class = "qr">{{ qrInfo.pos2 }}</div>
                <div class = "qr">{{ qrInfo.pos3 }}</div>
                <div class = "qr">{{ qrInfo.pos4 }}</div>
                <input class = "otp" maxlength="1" v-model="qrInfo.num1" @keyup="focusToTwo"/>
                <input class = "otp" maxlength="1" v-model="qrInfo.num2" @keyup="focusToThree" ref="qrIn2"/>
                <input class = "otp" maxlength="1" v-model="qrInfo.num3" @keyup="focusToFour" ref="qrIn3"/>
                <input class = "otp" maxlength="1" v-model="qrInfo.num4" ref="qrIn4"/>
            </div>
            <button class = "submit_form" @click="sendRequest" style="top: 588px;">확인</button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref, inject, onMounted} from 'vue'

const numberSet = new Set()
const qrInfo = ref({
    pos1: 0, pos2: 0, pos3: 0, pos4: 0,
    num1: "", num2: "", num3: "", num4: ""
})
const qrRequestDto = ref({
    position: [],
    number: ""
})

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
// 이런 방식으로 전역변수 사용 가능 편하다!

function getRandomNumber(limit){ // 중복되지 않는 난수 생성
    while(limit > numberSet.size){
        numberSet.add(Math.round(Math.random() * 14 + 1))
    }

    const numberArray = [...numberSet]
    qrInfo.value.pos1 = numberArray[0]; 
    qrRequestDto.value.position.push(numberArray[0]);
    qrInfo.value.pos2 = numberArray[1]; 
    qrRequestDto.value.position.push(numberArray[1]);
    qrInfo.value.pos3 = numberArray[2]; 
    qrRequestDto.value.position.push(numberArray[2]);
    qrInfo.value.pos4 = numberArray[3];
    qrRequestDto.value.position.push(numberArray[3]);
}

function sendRequest(){
    qrRequestDto.value.number = qrInfo.value.num1 + qrInfo.value.num2 + qrInfo.value.num3 + qrInfo.value.num4;
    axios.post(requestURL + "checkQr", qrRequestDto.value, {headers})
         .then((resp) => {
            if(resp.status === 200)
                router.replace('machineAuth')
         })
         .catch((error) => {
            if(error.response.data.error.message === "Qr코드가 틀려부러쓰")
                alert("잘못된 QR을 입력하셨습니다!")
         })
}

// 난수 자릿수 생성
onMounted(() => {
    getRandomNumber(4)  
})

// 여기부터는 입력하면 옆 칸으로 넘어가는 focus 부분
const qrIn2 = ref(null);
const qrIn3 = ref(null);
const qrIn4 = ref(null);

function focusToTwo(){
    qrIn2.value.focus()
}

function focusToThree(){
    qrIn3.value.focus()
}

function focusToFour(){
    qrIn4.value.focus()
}
</script>

<style>
@import "../style/wmistyle.css";
</style>