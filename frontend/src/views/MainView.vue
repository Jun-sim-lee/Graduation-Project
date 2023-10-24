<template>
    <div class = "screen">
        <div class = "inner_screen">
            <div class = "inner_header">
                <img class = "profile" src="../assets/profile_image.png" alt="Profile"/>
                <p style="color: white; font-family: 'SUITE-Regular';">{{ mainPageUsername }}님 반갑습니다!</p>
                <div style = "width: 120px;"></div>
                <button class = "gear" @click="moveToOption"></button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 130px; font-size: 30px; font-weight: bold; font-family: 'SUITE-Regular';">Where am I ?</p>
            <p style = "position: absolute; color: white; left: 27px; top: 181px; font-family: 'SUITE-Regular';">주변 기기를 당신의 마음대로 편하게 제어하세요<br>
                시간은 걱정마세요, 빠른 길로 안내합니다.</p>
        </div>
        <div style="width: 389px; height: 450px; position: relative; background-color: white;">
            <button class = "big_btn" @click="moveToMap">지도</button>
            <button class = "small_btn1" style = "left: 24px;" @click="moveToAll">
                전체 리소스
            </button>
            <button class = "small_btn2" style = "left: 201px;" @click="moveToMy">
                나의 리소스
            </button>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref, onMounted} from 'vue'
import { useStore } from 'vuex';

const mainPageUsername = ref({})
const store = useStore();

onMounted(() => {
    mainPageUsername.value = store.state.username
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
function moveToOption(){
    router.push('option')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>