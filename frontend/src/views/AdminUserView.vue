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
                    :key="user.nickName" v-for="user in userList">
                    <span style="font-size: 12px; line-height: 35px;">{{ user.nickName }}</span>
                    <span style="width: 130px; font-size: 12px; line-height: 35px;">({{ user.email }})</span>
                    <select v-model="user.authority" style="margin: 6px; height: 20px;">
                        <option :key="auth" v-for="auth in authList">{{ auth }}</option>
                    </select>
                    <button @click="changeAuthority(user.email, user.authority)" style="border: none; border-radius: 30px; background-color: blue; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">변경</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted, inject} from 'vue'
import {router} from '@/router'
import axios from 'axios';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')

const userList = ref([])
const authList = ref([
    "일반", "학생", "교수", "관리자"
])
const userInfoDto = ref({
    email: "",
    auth: ""
})

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestUserList();
})

function requestUserList(){
    axios.get(requestURL + "members", {headers})
        .then((resp) => {
            userList.value = resp.data;
            convertAuthority();
        })
}

function convertAuthority(){
    userList.value.forEach(element => {
        if(element.authority === 'None')
            element.authority = '일반'
        if(element.authority === 'Student')
            element.authority = '학생'
        if(element.authority === 'Professor')
            element.authority = '교수'
        if(element.authority === 'Admin')
            element.authority = '관리자'
    });
}

function changeAuthority(targetEmail, targetRole){
    userInfoDto.value.email = targetEmail;
    userInfoDto.value.auth = targetRole;

    axios.post(requestURL + "admin/upgradeAuth", userInfoDto.value, {headers})
       .then((resp) => {
            if(resp.status === 200)
                alert("권한 수정이 완료 되었습니다.")
       })
       .catch((error) => {
            alert(error)
       })

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