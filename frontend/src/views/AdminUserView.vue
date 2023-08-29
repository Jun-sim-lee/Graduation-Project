<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white;">
            <div style=" margin: auto; width: 330px; height: 50px; display: flex; justify-content:space-between; border-bottom: 2px solid black;">
                <div></div>
                <button class = "admin_button">사용자 목록</button>
                <button @click="moveToAsk" class = "admin_button" style="color: lightgray;">요청 목록</button>
                <button @click="moveToResource" class = "admin_button" style="color: lightgray;">리소스 목록</button>
                <div></div>
            </div>
            <ul>
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="user.name" v-for="user in userList">
                    <span style="font-size: 12px; line-height: 35px;">{{ user.name }}</span>
                    <span style="width: 130px; font-size: 12px; line-height: 35px;">({{ user.email }})</span>
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

const userList = ref([
    {name: "이*경", email: "anfrhrl98@pusan.ac.kr", role:"학생" },
    {name: "이*희", email: "abc980823@pusan.ac.kr", role:"교수"},
    {name: "심*섭", email: "dndlzm123@pusan.ac.kr", role:"학생"}
])
const authList = ref([
    "학생", "교수"
])
function moveToAsk(){
    router.replace('adminAsk')
}

function moveToResource(){
    router.replace('adminResource')
}
</script>

<style>
@import "../style/wmistyle.css";
</style>