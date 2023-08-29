<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white;">
            <div style="margin: auto; width: 330px; height: 50px; display: flex; justify-content:space-between; border-bottom: 2px solid black;">
                <div></div>
                <button @click="moveToUser" class = "admin_button" style="color: lightgray;">사용자 목록</button>
                <button @click="moveToAsk" class = "admin_button" style="color: lightgray;">요청 목록</button>
                <button class = "admin_button">리소스 목록</button>
                <div></div>
            </div>
            <ul>
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="resource.name" v-for="resource in resourceList">
                    <span style="width: 50px; font-size: 12px; line-height: 35px;">{{ resource.name }}</span>
                    <span style="font-size: 12px; line-height: 35px;">({{ resource.pos_x }}, {{resource.pos_y}})</span>
                    <select style="margin: 6px; height: 20px;">
                        <option :key="auth" v-for="auth in authList">{{ auth }}</option>
                    </select>
                    <button style="border: none; border-radius: 30px; background-color: blue; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">변경</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import {ref} from 'vue'
import {router} from '@/router'
//import { useStore } from 'vuex';

//const store = useStore();
//const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
//const requestURL = inject('requestURL')

const resourceList = ref([
    {name: "자판기", pos_x: 3, pos_y: 4, role:"학생"},
    {name: "도어락", pos_x: 11, pos_y: 4,role:"교수"},
    {name: "전등", pos_x: 12, pos_y: 4, role:"학생"},
    {name: "모니터", pos_x: 17, pos_y: 4, role:"교수"}                                                                                                                              
])
const authList = ref([
    "학생", "교수"
])

function moveToUser(){
    router.replace('adminUser')
}

function moveToAsk(){
    router.replace('adminAsk')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>