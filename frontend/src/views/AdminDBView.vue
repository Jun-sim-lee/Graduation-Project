<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white; position: relative">
            <div style=" width: 330px; height: 40px; border-bottom: 2px solid black;
                         position: absolute; top: 20px; left: 20px;">
                <p class = "admin_button">사용자 로그인 제한 해제</p>
                <input type="text" placeholder="사용자 이메일" v-model="resetRequestDto.email"> <button @click="unlock">해제</button>
            </div>
            <div style=" width: 330px; height: 40px; border-bottom: 2px solid black; margin: auto;
                         position: absolute; top: 110px; left: 20px">
                <p class = "admin_button">교수 권한 사용자 QR 코드 등록</p>
                <input type="text" placeholder="사용자 이메일" v-model="profDto.email"><br>
                <input type="text" placeholder="QR 코드" v-model="profDto.qrCode"> <button @click="registQR">등록</button>
            </div>
            <div style=" width: 330px; height: 40px; border-bottom: 2px solid black; margin: auto;
                         position: absolute; top: 220px; left: 20px">
                <p class = "admin_button">사용자별 기기 인증용 고유 코드 등록</p>
                <input type="text" placeholder="사용자 이메일" v-model="uniqueDto.email"><br>
                <input type="text" placeholder="고유 코드" v-model="uniqueDto.code"> <button @click="registUniqueCode">등록</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref, inject} from 'vue'
import axios from 'axios'

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestUrl = inject('requestURL')

const resetRequestDto = ref({
    email: ""
})
const profDto = ref({
    email: "",
    qrCode: ""
})
const uniqueDto = ref({
    email: "",
    code: ""
})

function unlock(){
    axios.post(requestUrl + "admin/reset", resetRequestDto.value)
        .then((resp) => {
            if(resp.status === 200)
                alert("로그인 기능 제한이 해제되었습니다.")
        })
}

function registQR(){
    axios.post(requestUrl + "admin/setQr", profDto.value, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("교수용 QR 등록이 완료되었습니다.")
        })
}

function registUniqueCode(){
    axios.post(requestUrl + "admin/setRpi", uniqueDto.value, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("등록이 완료되었습니다.")
        })
}

</script>

<style>
@import "../style/wmistyle.css";
</style>