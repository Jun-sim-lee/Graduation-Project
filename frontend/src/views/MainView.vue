<template>
    <div class = "screen">
        <div class = "inner_screen">
            <div class = "inner_header">
                <img class = "profile" src="../assets/logo.png" alt="Profile"/>
                <p style="color: white;">{{mainPageDto.username}}님 반갑습니다!</p>
                <div style = "width: 120px;"></div>
                <button class = "gear"></button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 130px; font-size: 30px; font-weight: bold; font-family: serif;">Where am I ?</p>
            <p style = "position: absolute; color: white; left: 27px; top: 181px;">주변 기기를 당신의 마음대로 편하게 제어하세요<br>
                시간은 걱정마세요, 빠른 길로 안내합니다.</p>
        </div>
        <div style="width: 389px; height: 450px; position: relative; background-color: white;">
            <button class = "big_btn" @click="moveToMap">지도</button>
            <button class = "small_btn" style = "left: 24px;" @click="moveToAll">
                전체<br> 
                리소스
            </button>
            <button class = "small_btn" style = "left: 201px;" @click="moveToMy">
                나의<br>
                리소스
            </button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios';
import {ref} from 'vue'
const mainPageDto = ref({})

axios.get('http://localhost:8080/main')
     .then((resp) => {
        mainPageDto.value = resp.data
     })
     .catch((error) => {
        alert(error)
     })

function moveToAll(){
    router.push('allResource')
}
function moveToMy(){
    router.push('myResource')
}
function moveToMap(){
    router.push('map')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>