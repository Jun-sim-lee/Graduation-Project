<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input class = "wmi_input" style = "top: 422px; padding-left: 3%;" placeholder="아이디" v-model="userInfoDto.userId"/>
        <input type = "password" class = "wmi_input" style = "top: 477px; padding-left: 3%;" placeholder="비밀번호" v-model="userInfoDto.password"/>
        <button class = "submit_form" style = "top: 570px" @click="sendRequest">로그인</button>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref} from 'vue'

const userInfoDto = ref({
        userId: "",
        password: ""
})

function sendRequest(){
    axios.post("http://localhost:8080/login", userInfoDto.value)
         .then((resp) => {
            if(resp.data.accessToken)
                localStorage.setItem('user', JSON.stringify(resp.data));
            if(resp.data === "user")
                router.replace('main')
            else if(resp.data === "student")
                router.replace('otp')
            else if(resp.data === "professor")
                router.replace('qr')
            else
                alert("없는 유저 입니다.")
         })
         .catch((error) => {
            alert(error);
         })
}
</script>

<style>
@import "../style/wmistyle.css";
</style>