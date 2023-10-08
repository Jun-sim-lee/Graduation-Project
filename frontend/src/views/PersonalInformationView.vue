<template>
    <div class = "screen">
        <div style="width: 389px; height: 50px; background-color: #537FE7;">
            <button @click="moveToPrev" style="line-height: 40px; color: white; background-color: transparent; 
                                               padding-left: 20px; border: none; font-size: 20px; font-family: 'SUITE-Regular';"> &lt; 개인 정보 변경</button>
        </div>
        <div style="width: 389px; height: 660px; background-color: white; position: relative;">
            <img src="../assets/personal.png" style="position: absolute; left: 85px;">
            <input class = "wmi_input" style = "top: 190px; left: 60px; padding-left: 3%;" placeholder="닉네임" v-model="userInfoDto.nickName"/>
            <input type="password" class = "wmi_input" style = "top: 240px; left: 60px; padding-left: 3%;" placeholder="새 비밀번호" v-model="userInfoDto.password"/>
            <input type="password" v-model = "checkedPw" style="top: 290px; left: 60px; padding-left: 3%;" placeholder="비밀번호 확인"
               v-bind:class="{'wmi_input': validPw, 'input_error': invalidPw}"/>
            <p v-if="validPw === false" style="position: absolute; top: 325px; left: 120px; font-size: 12px; color: red;">입력된 비밀번호와 다릅니다.</p>
            <button class = "submit_form" style = "top: 380px; left: 67px;" @click="changePassword">비밀번호 변경</button>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import { ref, inject } from 'vue';
import { useStore } from 'vuex';
import {router} from '@/router'

const store = useStore();
const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
const validPw = ref(true)
const invalidPw = ref(false)
const checkedPw = ref()
const userInfoDto = ref({
    nickName: store.state.username,
    password: ""
})

function changePassword(){
    checkInformation()
    
    if(validPw.value === true){
        axios.post(requestURL + "changeInfo", userInfoDto.value, {headers})
            .then((resp) => {
                if(resp.status === 200){
                    alert("개인 정보 변경이 완료되었습니다.")
                    router.go(-1)
                }
            })
    }
}

function checkInformation(){
    if (userInfoDto.value.password === checkedPw.value){
        validPw.value = true
        invalidPw.value = false
    }
    else{
        validPw.value = false
        invalidPw.value = true
    }
}

function moveToPrev(){
    router.go(-1)
}

</script>

<style>
@import "../style/wmistyle.css";
</style>