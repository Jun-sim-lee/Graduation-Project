<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white; position: relative;">
            <button class="admin_main_button" style = "top: 120px;" @click="moveToAsk"> 리소스 권한 설정 및 요청 목록 </button>
            <button class="admin_main_button" style = "top: 240px;" @click="moveToDB"> 사용자 DB 제어판 </button>
            <button class="admin_main_button" style = "top: 360px;" @click="moveToResourceControl"> 리소스 DB 제어판 </button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';

function moveToAsk(){
    router.push('adminUser')
}

function moveToDB(){
    router.push('adminDB')
}

function moveToResourceControl(){
    router.push('adminResourceControl')
}

</script>

<style>
@import "../style/wmistyle.css";
</style>