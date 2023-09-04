<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white; position: relative">
            <div style=" width: 330px; height: 40px; border-bottom: 2px solid black;
                         position: absolute; top: 20px; left: 20px;">
                <p class = "admin_button">사용자 로그인 제한 해제</p>
                <input type="text" placeholder="사용자 이메일" v-model="email"> <button @click="unlock">해제</button>
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
                <input type="text" placeholder="고유 코드" v-model="uniqueDto.uniqueCode"> <button @click="registUniqueCode">등록</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'

const email = ref()
const profDto = ref({
    email: "",
    qrCode: ""
})
const uniqueDto = ref({
    email: "",
    uniqueCode: ""
})

function unlock(){
    // axios.post(requestUrl + "unlock", email.value)
}

function registQR(){
    // axios.post(requestUrl + "registQR", profDto.value)
}

function registUniqueCode(){
    // axios.post(requestUrl + "registUniqueCode", uniqueDto.value)
}

</script>

<style>
@import "../style/wmistyle.css";
</style>