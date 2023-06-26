<template>        
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input v-model="email" class = "wmi_input" style="top: 377px; padding-left: 3%;" placeholder="부산대학교 이메일"/>
        <button @click="sendRequest" class = "submit_form" style="top: 488px">이메일 인증하기</button>
        <button @click="toMain" class = "submit_form" style="top: 538px">인증없이 진행하기</button>
        <p style = "position: absolute; top: 588px; width: 250px; font-size: 15px; color: #808080;">※ 주의사항!<br>
            <br>
            학교 이메일 인증 없이 진행하실 경우<br>
            현재 내 위치 확인만 가능합니다.<br>
            <br>
            추후 이메일 인증을 진행하실 수 있습니다.
        </p>
    </div>
</template>

<script setup>
import {router} from '@/router'
import axios from 'axios';
import {ref} from 'vue';

const email = ref()
const validEmail = ref(true)

function sendRequest(){
    if(validEmail.value === true)
        axios.post("http://localhost:8080/emailAuth", email.value)
             .then((resp) => {
                console.log(resp)
             })
             .catch((error) => {
                console.log(error)
             })
}
function toMain(){
    router.replace('main')
}
</script>
<style>
@import "../style/wmistyle.css";
</style>