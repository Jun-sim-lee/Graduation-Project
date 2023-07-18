<template>
    <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <h1 style="position: absolute; top: 330px; font-size: 20px">QR 인증입니다.</h1>
            <h1 style="position: absolute; top: 330px; font-size: 20px">각 자리에 해당하는 문자를 입력해 주십시오.</h1>
            <div class = "qr_wrapper">
                <div class = "qr">{{ qrDto.ps1 }}</div>
                <div class = "qr">{{ qrDto.ps2 }}</div>
                <div class = "qr">{{ qrDto.ps3 }}</div>
                <div class = "qr">{{ qrDto.ps4 }}</div>
                <input class = "otp" maxlength="1" v-model="qrDto.qr1"/>
                <input class = "otp" maxlength="1" v-model="qrDto.qr2"/>
                <input class = "otp" maxlength="1" v-model="qrDto.qr3"/>
                <input class = "otp" maxlength="1" v-model="qrDto.qr4"/>
            </div>
            <button class = "submit_form" @click="sendRequest" style="top: 588px;">확인</button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref} from 'vue'
const qrDto = ref({
    ps1: Math.round(Math.random() * 14 + 1),
    ps2: Math.round(Math.random() * 14 + 1),
    ps3: Math.round(Math.random() * 14 + 1),
    ps4: Math.round(Math.random() * 14 + 1),
    qr1: "", qr2: "", qr3: "", qr4: ""
})

function sendRequest(){
    axios.post("http://localhost:8080/qr", qrDto.value)
         .then((resp) => {
            if(resp === "OK")
                router.replace('machineAuth')
         })
}
</script>

<style>
@import "../style/wmistyle.css";
</style>