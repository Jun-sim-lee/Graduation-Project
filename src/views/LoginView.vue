<template>
    <head>
        <meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests"/>
    </head>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input class = "wmi_input" style = "top: 422px; padding-left: 3%;" placeholder="아이디" v-model="loginDto.userId"/>
        <input type = "password" class = "wmi_input" style = "top: 477px; padding-left: 3%;" placeholder="비밀번호" v-model="loginDto.password"/>
        <button class = "submit_form" style = "top: 570px" @click="sendRequest">로그인</button>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref} from 'vue'
import { useStore } from 'vuex';

const store = useStore();
const loginDto = ref({
        userId: "",
        password: ""
})
const wrongCount = ref(0)

function sendRequest(){
    axios.post("http://localhost:8080/login", loginDto.value)
         .then((resp) => {
            if(resp.status === 200){
                store.commit("login", resp.data)
                localStorage.setItem('accessToken', JSON.stringify(resp.data.accessToken));
                wrongCount.value = 0;
                localStorage.setItem('wrongCount', wrongCount.value);
            }
            else if(resp.data === "존재하지 않는 아이디입니다."){
                alert("존재하지 않는 아이디입니다!")
            }
            else if(resp.data === "비밀번호가 틀렸습니다."){
                wrongCount.value++;
                
                if(localStorage.getItem('wrongCount') === 5){
                    alert("비밀번호 5회 오입력으로, 로그인 기능이 제한됩니다.");
                }

                alert("잘못된 비밀번호 입니다.\n잘못된 비밀번호 5회 입력 시 로그인 기능이 제한됩니다."
                + "(" + wrongCount.value +  "회 틀림)")
            }

            if(resp.data.authority === "user")
                router.replace('main')
            else if(resp.data.authority === "student")
                router.replace('otp')
            else if(resp.data.authority === "professor")
                router.replace('qr')
         })
         .catch((error) => {
            alert(error);
         })
}
</script>

<style>
@import "../style/wmistyle.css";
</style>