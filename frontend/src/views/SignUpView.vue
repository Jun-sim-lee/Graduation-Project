<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input :class = "{'wmi_input': validEmail, 'input_error': invalidEmail}" v-model = "signUpDto.email" 
                style="top: 320px; padding-left: 3%;" placeholder="이메일"/>
        <p v-if="invalidEmail === true" style="position: absolute; top: 355px; font-size: 12px; color: red;">올바르지 않은 형식의 이메일입니다.</p>
        <input class = "wmi_input" v-model = "signUpDto.nickName" style="top: 388px; padding-left: 3%;" placeholder="닉네임"/>
        <input type="password" class = "wmi_input" v-model = "signUpDto.password" style="top: 444px; padding-left: 3%;" placeholder="비밀번호는 8자리 이상이어야 합니다."/>
        <input type="password" v-model = "checkedPw" style="top: 499px; padding-left: 3%;" placeholder="비밀번호 확인"
               v-bind:class="{'wmi_input': validPw, 'input_error': invalidPw}"/>
        <p v-if="validPw === false" style="position: absolute; top: 535px; font-size: 12px; color: red;">입력된 비밀번호와 다릅니다.</p>
        <button class = "submit_form" @click = "sendRequest" style="top: 577px;">회원가입</button>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref, inject} from 'vue'

const requestURL = inject('requestURL')

const regex = /^[A-Za-z0-9]+@pusan.ac.kr/
const validPw = ref(true)
const invalidPw = ref(false)
const validEmail = ref(true)
const invalidEmail = ref(false)
const checkedPw = ref()
const signUpDto = ref({
    email: "",
    nickName: "",
    password: ""
})

function checkInformation(){
    if (signUpDto.value.password === checkedPw.value){
        validPw.value = true
        invalidPw.value = false
    }
    else{
        validPw.value = false
        invalidPw.value = true
    }

    if(regex.test(signUpDto.value.email) === false){
        validEmail.value = false
        invalidEmail.value = true
    }
    else{
        validEmail.value = true
        invalidEmail.value = false
    }
}

function sendRequest(){
    checkInformation();
    
    if(validPw.value === true && validEmail.value === true){
        axios.post(requestURL + "signUp", signUpDto.value)
            .then((resp) => {
                if(resp.data === "ok")
                    router.replace('login')
            })
            .catch((error) => {
                if(error.response.data.error.message === "이미 존재하는 이메일입니다."){
                    alert("이미 존재하는 이메일입니다!")
                }
            })
    }
}
</script>

<style>
@import "../style/wmistyle.css";
</style>