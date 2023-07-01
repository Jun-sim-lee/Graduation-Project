<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo"/>
        <h1 style="position: absolute; top: 380px; font-size: 25px">기기
            <span style="color: gray;">(라즈베리파이)</span>
            를 켜주시기 바랍니다.</h1>
        <button class="submit_form" @click="sendRequest" style="top: 488px">완료</button>
    </div>
</template>

<script setup>
import { router } from '@/router';

function sendRequest(){
    alert("요청을 전송합니다.")
    router.replace('main')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>