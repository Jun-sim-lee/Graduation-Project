<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo"/>
        <h1 style="position: absolute; top: 380px; font-size: 20px">기기
            <span style="color: gray;">(라즈베리파이)</span>
            를 켜주시기 바랍니다.</h1>
        <button class="submit_form" @click="sendRequest" style="top: 488px">완료</button>
    </div>
</template>

<script setup>
import { router } from '@/router'

function sendRequest(){
    /*
    axios.post('http://localhost:8080/frontRpiAuth', uniqueRpiCode.value)
     .then((resp) => {
        if(resp.data === "Valid"){
            alert(resp.data + " 이므로 입장합니다.")
            router.replace('main')
        }
        else{
            alert("유효하지 않은 코드입니다!!")
            router.replace('')
        }
     })
     .catch((error) => {
        alert(error)
     })
     */
    router.replace('main')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>