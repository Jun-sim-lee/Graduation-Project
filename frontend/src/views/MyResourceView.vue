<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 나의 리소스</button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: serif;">My Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px;">직접 추가한 리소스 목록입니다.<br>
                근처에 도달하면 자원을 자유롭게 제어할 수 있습니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white;">
            <ul>
                <li style="display: flex; flex-direction: row; justify-content: space-between;"
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
import {ref} from 'vue';
import { router } from '@/router';
const resourceList = ref([
    {image: "../assets/logo.png", name: "자판기"},
    {image: "../assets/logo.png", name: "도어락"},
    {image: "../assets/logo.png", name: "전등"},
    {image: "../assets/logo.png", name: "모니터"}
])

function moveToPrev(){
    router.go(-1)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>