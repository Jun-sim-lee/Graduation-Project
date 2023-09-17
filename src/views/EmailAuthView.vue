<template>        
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input v-model="emailAuthDto.email" :class = "{'wmi_input': valid, 'input_error': invalid}" style="top: 355px; padding-left: 3%;" placeholder="부산대학교 이메일"/>
        <p v-if="invalid === true" style="position: absolute; top: 390px; font-size: 12px; color: red;">올바르지 않은 형식의 이메일입니다.</p>

        <div style="position: absolute; top: 420px; width: 279px;">
            <input v-model="emailAuthDto.authNum" style="background: rgba(211, 211, 211, 0.6); border-radius: 50px; border: none;
                   width: 155px; height:39px; padding-left: 5%; margin-right: 10px;" placeholder="인증번호"/>
            <button @click="sendAuthKey" class = "submit_form" style="width: 100px;">인증번호 요청</button>
        </div>

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
import {ref, inject} from 'vue';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')

const emailAuthDto = ref({
    email: "",
    authNum: ""
})
const regex = /^[A-Za-z0-9]+@pusan.ac.kr/
const valid = ref(true)
const invalid = ref(false)
const authKeyValid = ref(true)

function sendAuthKey(){
    axios.post(requestURL + "sendEmail", emailAuthDto.value.email, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("인증번호를 서버에 요청합니다.\n이메일을 확인해주세요!")
    })
}

function sendRequest(){
    validate()
    authKeyValidate()

    if(valid.value === true && authKeyValid.value === true)
        axios.post(requestURL + "emailAuth", emailAuthDto.value, {headers})
             .then((resp) => {
                if(resp.status === 200)
                    router.replace('main')
             })
             .catch((error) => {
                if(error.response.data.error.message === "인증번호가 틀려부러쓰")
                    alert(error.response.data.error.message)
             })
}

function validate(){
    if(regex.test(emailAuthDto.value.email) === false){
        valid.value = false
        invalid.value = true
    }
    else{
        valid.value = true
        invalid.value = false
    }
}

function authKeyValidate(){
    if(emailAuthDto.value.authNum < 6){
        authKeyValid.value = false
        alert("올바른 형식의 인증번호를 입력 해주세요!")
    }
    else
        authKeyValid.value = true
}

function toMain(){
    router.replace('main')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>