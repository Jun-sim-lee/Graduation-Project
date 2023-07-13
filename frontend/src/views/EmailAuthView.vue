<template>        
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input v-model="emailDto.email" :class = "{'wmi_input': valid, 'input_error': invalid}" style="top: 377px; padding-left: 3%;" placeholder="부산대학교 이메일"/>
        <p v-if="invalid === true" style="position: absolute; top: 415px; font-size: 12px; color: red;">올바르지 않은 형식의 이메일입니다.</p>
        <button @click="sendRequest" class = "submit_form" style="top: 488px">이메일 인증하기</button>
        <button @click="toLogin" class = "submit_form" style="top: 538px">인증없이 진행하기</button>
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

const emailDto = ref({
    email: ""
})
const regex = /^[A-Za-z0-9]+@pusan.ac.kr/
const valid = ref(true)
const invalid = ref(false)

function sendRequest(){
    validate()

    if(valid.value === true)
        axios.post("http://localhost:8080/emailAuth", emailDto.value)
             .then((resp) => {
                console.log(resp)
                if(resp.data === "OK")
                    router.replace('main')
             })
             .catch((error) => {
                alert(error)
             })
}

function validate(){
    if(regex.test(emailDto.value.email) === false){
        valid.value = false
        invalid.value = true
    }
    else{
        valid.value = true
        invalid.value = false
    }
}

function toLogin(){
    router.replace('login')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>