<template>
    <div class="modal_black" v-if="showModal">
        <div class="option_modal_window">
            <p style="font-family: 'SUITE-Regular';" @click="closeModal">X</p>
            <p style="text-align: center;">현재 사용중인 비밀번호를 입력해 주십시오.</p>
            <input type="password" class = "wmi_input" style = "position: absolute; top: 120px; left: 35px; padding-left: 3%;" placeholder="현재 비밀번호" v-model="passwordDto.password"/>
            <button class = "submit_form" style = "top: 180px; left: 40px;" @click="closeModalWithRequest">입력</button>
        </div>
    </div>
    <div class = "screen">
        <div style="width: 389px; height: 50px; background-color: #537FE7;">
            <button @click="moveToPrev" style="line-height: 40px; color: white; background-color: transparent; 
                                               padding-left: 20px; border: none; font-size: 20px; font-family: 'SUITE-Regular';"> &lt; 설정</button>
        </div>
        <div style="width: 389px; height: 660px; background-color: white;">
            <ul>
                <li class="resource_list" @click="checkPassword">개인 정보 관리</li>
                <li class="resource_list">버전 확인</li>
                <li class="resource_list" @click="moveToEmail">이메일 인증</li>
                <li class="resource_list" @click="logout" style="color: red;">로그아웃</li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref, inject} from 'vue'
import axios from 'axios';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
const showModal = ref(false)
const passwordDto = ref({
    password : ""
})

function moveToEmail(){
    router.push('emailAuth')
}
function moveToPrev(){
    router.go(-1)
}
function logout(){
    localStorage.clear()
    router.replace('/')
}

function checkPassword(){
    showModal.value = true
}

function closeModal(){
    showModal.value = false
}

function closeModalWithRequest(){
    axios.post(requestURL + "checkPassword", passwordDto.value ,{headers})
        .then((resp) => {
            if(resp.status === 200)
                router.replace('picontrol')
        })
        .catch((error) => {
            if(error.response.data.error.message === "비밀번호가 틀려부러쓰"){
                alert("잘못된 비밀번호 입니다.")
            }
        })
}

</script>

<style>
@import "../style/wmistyle.css";
</style>