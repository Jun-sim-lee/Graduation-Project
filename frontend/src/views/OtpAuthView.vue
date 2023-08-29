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
import axios from 'axios';
import {ref, inject} from 'vue'
import { useStore } from 'vuex';

const store = useStore();
const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
const requestURL = inject('requestURL')
const otpDto = ref({
    otpCode1: "",
    otpCode2: "",
    otpCode3: "",
    otpCode4: ""
})

function sendRequest(){
    axios.post(requestURL + "otp", otpDto.value, {headers})
        .then((resp) => {
            if(resp.status === 200)
                router.replace('machineAuth')
        })
}

// 여기부터는 입력하면 옆 칸으로 이동하는 focus 부분
// 입력이 되어야 넘어가도록 조정 필요
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