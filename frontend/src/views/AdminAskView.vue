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
                    <span style="font-size: 12px; line-height: 35px;">{{ ask.deviceName }}({{ ask.x }}, {{ ask.y }})</span>
                    <span style="font-size: 12px; line-height: 35px;">{{ ask.nickName }}</span>
                    <div>
                        <button @click="requestPermission(ask.resourceId, ask.email, ask.askId)" style="border: none; border-radius: 30px; background-color: green; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">수락</button>
                        <button @click="requestRejection(ask.askId)" style="border: none; border-radius: 30px; background-color: red; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">반려</button>
                    </div>
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

const askList = ref([
])
const permissionRequestDto = ref({
    email: "",
    deviceId: 0,
    askId: 0
})

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestAskList();
})

function requestAskList(){
    axios.get(requestURL + "admin/askList", {headers})
        .then((resp) => {
            askList.value = resp.data;
        })
}

function requestRejection(targetId){
    axios.delete(requestURL + "admin/rejectAsk/" + targetId, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("요청이 반려 되었습니다.")
            router.go(0)
        })
        .catch((error) => {
            alert(error)
        })

    // 반려 요청을 어떻게?
    // 그냥 DB의 요청 목록에서 요청만 삭제하면 됨
    // 사용자에게 반려 사실을 알려주어야 하는가?
}

function requestPermission(targetId, targetEmail, askId){
    permissionRequestDto.value.deviceId = targetId
    permissionRequestDto.value.email = targetEmail
    permissionRequestDto.value.askId = askId

    axios.post(requestURL + "admin/acceptAsk", permissionRequestDto.value, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("요청이 수락되었습니다.")
            router.go(0)
        })
        .catch((error) => {
            alert(error)
        })

    // 허가 요청을 어떻게?
    // 1. 유저 이름을 받는다.
    // 2. 리소스 이름을 받는다.
    // 3. 해당 유저의 리소스 리스트에 추가를 해야함.
}

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