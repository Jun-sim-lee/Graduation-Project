<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 전체 리소스</button>
                <button class = "gear" @click="clickGear"></button>
            </div>
            <div :class="{'hidden_drawer': gearClick}">
                <ul class="drawer"
                    style="position: absolute; top: 0px; right: 30px; 
                           background-color: white; width: 100px; height: 100px; border-radius: 20px;">
                    <li class="drawer_list" style="border-bottom: 1px solid #808080;" @click="sortAll"> 전체 </li>
                    <li class="drawer_list" style="border-bottom: 1px solid #808080;" @click="sortMine"> 권한 내 리소스 </li>
                    <li class="drawer_list" @click="sortForbidden"> 권한 외 리소스 </li>
                </ul>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: serif;">All Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px;">모든 리소스 목록입니다.<br>
                정렬을 통해 권한 별 리소스 확인이 가능합니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white;">
            <ul>
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="resource.name" v-for="resource in resourceList">
                    <img src="../assets/fluke.png" style="width: 25px; height: 25px;">
                    <span style="font-size: 18px; line-height: 35px;">{{ resource.name }}</span>
                    <button style="background-color: red; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">삭제</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref} from 'vue'

const gearClick = ref(true)
const resourceList = ref([
    {image: "../assets/logo.png", name: "자판기"},
    {image: "../assets/logo.png", name: "도어락"},
    {image: "../assets/logo.png", name: "전등"},
    {image: "../assets/logo.png", name: "모니터"}
])

function moveToPrev(){
    router.go(-1)
}
function sortAll(){
    alert("전체 정렬")
}
function sortMine(){
    alert("권한 내 정렬")
}
function sortForbidden(){
    alert("권한 외 정렬")
}
function clickGear(){
    gearClick.value = !gearClick.value
}
</script>

<style>
@import "../style/wmistyle.css";
</style>