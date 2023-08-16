<template>
    <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <p style = "position: absolute; top: 388px;">인증번호 4자리를 입력해주세요.</p>
            <div class = "otp_wrapper">
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode1" @keyup="focusToTwo"/>
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode2" @keyup="focusToThree" ref="otpIn2"/>
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode3" @keyup="focusToFour" ref="otpIn3"/>
                <input class = "otp" maxlength="1" style="margin: 3px;" v-model="otpDto.otpCode4" ref="otpIn4"/>
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

// 여기부터는 입력하면 옆 칸으로 이동하는 focus 부분
const otpIn2 = ref(null);
const otpIn3 = ref(null);
const otpIn4 = ref(null);

function focusToTwo(){
    otpIn2.value.focus()
}

function focusToThree(){
    otpIn3.value.focus()
}

function focusToFour(){
    otpIn4.value.focus()
}
</script>

<style>
@import "../style/wmistyle.css";
</style>