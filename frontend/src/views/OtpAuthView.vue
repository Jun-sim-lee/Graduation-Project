<template>
    <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <p style = "position: absolute; top: 388px;">인증번호 4자리를 입력해주세요.</p>
            <div class = "otp_wrapper">
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode1"/>
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode2"/>
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode3"/>
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode4"/>
            </div>
            <button class = "submit_form" style="top: 588px;" @click="sendRequest">확인</button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref} from 'vue'
const otpDto = ref({
    otpCode1: "",
    otpCode2: "",
    otpCode3: "",
    otpCode4: ""
})

function sendRequest(){
    alert("서버로 " + otpDto.value.otpCode1 + otpDto.value.otpCode2 + otpDto.value.otpCode3 + otpDto.value.otpCode4 + " 를 전송합니다.")
    router.replace('machineAuth')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>