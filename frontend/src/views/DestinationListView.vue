<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 목적지 설정</button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: 'SUITE-Regular'">Where to go?</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px; font-family: 'SUITE-Regular';">지정할 수 있는 목적지 목록입니다.<br>
                선택된 목적지까지 최단경로를 안내합니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white; overflow: scroll;">
            <ul>
                <li class = "resource_list" style = "text-align: center;" 
                    :key="destination.name" v-for="destination in destinationList">
                    <span @click="requestShortcut(destination.coord_x, destination.coord_y)" style="font-size: 18px; line-height: 30px;">{{ destination.name }}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { router } from '@/router';
import {ref} from 'vue'

const destinationList = ref([
    {name: "6208", coord_x: 10, coord_y: 11},
    {name: "남자화장실", coord_x: 3, coord_y: 11},
    {name: "여자화장실", coord_x: 17, coord_y: 4},
    {name: "후문 계단", coord_x: 15, coord_y: 4},
    {name: "자판기", coord_x:1, coord_y: 8},
    {name: "정문 계단", coord_x: 2, coord_y: 7},
    {name: "창의마루", coord_x: 16, coord_y: 4},
    {name: "쓰레기통", coord_x: 14, coord_y: 4},
    {name: "후문", coord_x: 17, coord_y: 4}
])

function requestShortcut(targetX, targetY){
    localStorage.setItem("x", targetX)
    localStorage.setItem("y", targetY)
    localStorage.setItem("isDest", true)
    
    router.go(-1)
}

function moveToPrev(){
    router.go(-1)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>