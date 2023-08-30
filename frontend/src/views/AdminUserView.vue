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
                    <select v-model="user.role" style="margin: 6px; height: 20px;">
                        <option :key="auth" v-for="auth in authList">{{ auth }}</option>
                    </select>
                    <button @click="changeAuthority(user.name)" style="border: none; border-radius: 30px; background-color: blue; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">변경</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted, inject} from 'vue'
import {router} from '@/router'
import { useStore } from 'vuex';
import axios from 'axios';

const store = useStore();
const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
const requestURL = inject('requestURL')

const userList = ref([
    {name: "이*경", email: "anfrhrl98@pusan.ac.kr", role:"학생" },
    {name: "이*희", email: "abc980823@pusan.ac.kr", role:"교수"},
    {name: "심*섭", email: "dndlzm123@pusan.ac.kr", role:"학생"}
])
const authList = ref([
    "학생", "교수"
])

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestUserList();
})

function requestUserList(){
    axios.get(requestURL + "adminUser", {headers})
        .then((resp) => {
            userList.value = resp.data;
        })
}

function changeAuthority(target){
    alert(target)
    // axios.post(requestURL + "changeAuthority", userInfo,{headers})
    //    .then((resp) => {})
    //    .catch((error) => {})

    // 여기서 보낼 유저 정보의 형식은?
    // 1. 유저 이메일
    // 2. 유저가 변경 되고 싶은 권한
    // 중요!) 현재 권한이랑 다른 경우에만 받아줘야 함.
}

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