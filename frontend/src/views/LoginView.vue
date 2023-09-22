<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input class = "wmi_input" style = "top: 422px; padding-left: 3%;" placeholder="아이디" v-model="loginDto.email"/>
        <input type = "password" class = "wmi_input" style = "top: 477px; padding-left: 3%;" placeholder="비밀번호" v-model="loginDto.password"/>
        <button class = "submit_form" style = "top: 570px" @click="sendRequest">로그인</button>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref, inject} from 'vue'
import { useStore } from 'vuex';

const requestURL = inject('requestURL')
const store = useStore();
const loginDto = ref({
        email: "",
        password: ""
})

function sendRequest(){
    axios.post(requestURL + "login", loginDto.value)
         .then((resp) => {
            if(resp.status === 200){
                localStorage.clear();
                
                store.commit("login", resp.data);
                localStorage.setItem('accessToken', resp.data.authTokenDTO.accessToken) // 이부분 고쳐야함 다시!! 
                router.replace('main')
            }

            if(resp.data.authority === "None")
                router.replace('machineAuth')
            else if(resp.data.authority === "Student")
                router.replace('otp')
            else if(resp.data.authority === "Professor")
                router.replace('qr')
            else if(resp.data.authority === "Admin")
                router.replace('adminMain')
         })
         .catch((error) => {
            if(error.response.data.error.message === "존재하지 않는 이메일입니다."){
                alert("존재하지 않는 이메일입니다!")
            }
            else if(error.response.data.error.message.search('회') != -1){
                alert(error.response.data.error.message + "\n5회 이상 오입력시 로그인 기능이 제한됩니다.")
            }
            else if(error.response.data.error.message === "기능이 제한된 계정입니다."){
                alert("기능이 제한된 계정입니다.\n관리자에게 문의 바랍니다.")
            }
         })
}
</script>

<style>
@import "../style/wmistyle.css";
</style>