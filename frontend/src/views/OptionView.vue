<template>
    <div class = "screen">
        <div style="width: 389px; height: 50px; background-color: #537FE7;">
            <button @click="moveToPrev" style="line-height: 40px; color: white; background-color: transparent; 
                                               padding-left: 20px; border: none; font-size: 20px"> &lt; 설정</button>
        </div>
        <div style="width: 389px; height: 611px; background-color: white;">
            <ul>
                <li>개인 정보 관리</li>
                <li>버전 확인</li>
                <li @click="moveToEmail">이메일 인증</li>
                <li @click="logout" style="color: red;">로그아웃</li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';

function moveToEmail(){
    router.push('emailAuth')
}
function moveToPrev(){
    router.go(-1)
}
function logout(){
    alert("로그아웃을 요청합니다.")
}
</script>

<style>
@import "../style/wmistyle.css";
</style>