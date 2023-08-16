<template>
    <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <h1 style="position: absolute; top: 330px; font-size: 20px">QR 인증입니다.</h1>
            <h1 style="position: absolute; top: 355px; font-size: 20px">각 자리에 해당하는 문자를 입력해 주십시오.</h1>
            <div class = "qr_wrapper">
                <div class = "qr">{{ qrDto.ps1 }}</div>
                <div class = "qr">{{ qrDto.ps2 }}</div>
                <div class = "qr">{{ qrDto.ps3 }}</div>
                <div class = "qr">{{ qrDto.ps4 }}</div>
                <input class = "otp" maxlength="1" v-model="qrDto.qr1" @keyup="focusToTwo"/>
                <input class = "otp" maxlength="1" v-model="qrDto.qr2" @keyup="focusToThree" ref="qrIn2"/>
                <input class = "otp" maxlength="1" v-model="qrDto.qr3" @keyup="focusToFour" ref="qrIn3"/>
                <input class = "otp" maxlength="1" v-model="qrDto.qr4" ref="qrIn4"/>
            </div>
            <button class = "submit_form" @click="sendRequest" style="top: 588px;">확인</button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref} from 'vue'

const numberSet = new Set()
const qrDto = ref({
    ps1: 0, ps2: 0, ps3: 0, ps4: 0,
    qr1: "", qr2: "", qr3: "", qr4: ""
})

function getRandomNumber(limit){ // 중복되지 않는 난수 생성
    while(limit > numberSet.size){
        numberSet.add(Math.round(Math.random() * 14 + 1))
    }

    const numberArray = [...numberSet]
    qrDto.value.ps1 = numberArray[0]; qrDto.value.ps2 = numberArray[1]; 
    qrDto.value.ps3 = numberArray[2]; qrDto.value.ps4 = numberArray[3];
}

function sendRequest(){
    axios.post("http://localhost:8080/qr", qrDto.value)
         .then((resp) => {
            if(resp === "OK")
                router.replace('machineAuth')
         })
}

getRandomNumber(4)

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