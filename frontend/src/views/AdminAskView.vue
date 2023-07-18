<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white;">
            <div style="margin: auto; width: 330px; height: 50px; display: flex; justify-content:space-between; border-bottom: 2px solid black;">
                <div></div>
                <button @click = "moveToUser" class = "admin_button" style="color: lightgray;">사용자 목록</button>
                <button class = "admin_button">요청 목록</button>
                <button @click="moveToResource" class = "admin_button" style="color: lightgray;">리소스 목록</button>
                <div></div>
            </div>
            <ul>
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="ask.name" v-for="ask in askList">
                    <span style="font-size: 12px; line-height: 35px;">{{ ask.name }}({{ ask.pos_x }}, {{ ask.pos_y }})</span>
                    <span style="font-size: 12px; line-height: 35px;">{{ ask.username }}</span>
                    <div>
                        <button style="border: none; border-radius: 30px; background-color: green; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">수락</button>
                        <button style="border: none; border-radius: 30px; background-color: red; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">반려</button>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import {router} from '@/router'

const askList = ref([
    { name: "강의실" ,username: "심*섭", pos_x: 10, pos_y: 11 },
    { name: "강의실", username: "이*희", pos_x: 4, pos_y: 11 },
    { name: "강의실", username: "이*경", pos_x: 7, pos_y: 4 }                                                 
])

function moveToUser(){
    router.replace('adminUser')
}

function moveToResource(){
    router.replace('adminResource')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>