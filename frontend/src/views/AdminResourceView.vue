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
                    :key="resource.id" v-for="resource in resourceList">
                    <span style="width: 80px; font-size: 12px; line-height: 35px;">{{ resource.deviceName }}</span>
                    <span style="font-size: 12px; line-height: 35px;">({{ resource.location.x }}, {{resource.location.y}})</span>
                    <select v-model="resource.auth" style="margin: 6px; height: 20px;">
                        <option :key="auth" v-for="auth in authList">{{ auth }}</option>
                    </select>
                    <button @click="changeAuthority(resource.id, resource.auth)" style="border: none; border-radius: 30px; background-color: blue; width: 50px; height: 30px;
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

const resourceList = ref([                                                                           
])
const authList = ref([
    "학생", "교수"
])
const changeAuthorityDto = ref({
    id: 0,
    auth: ""
})

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestAskList();
})

function convertAuthority(){
    resourceList.value.forEach(element => {
        if(element.auth === 'None')
            element.auth = '일반'
        if(element.auth === 'Student')
            element.auth = '학생'
        if(element.auth === 'Professor')
            element.auth = '교수'
    });
}

function requestAskList(){
    axios.get(requestURL + "resources", {headers})
        .then((resp) => {
            resourceList.value = resp.data;
            convertAuthority()
        })
}

function changeAuthority(targetId, targetAuth){
    changeAuthorityDto.value.id = targetId;
    changeAuthorityDto.value.auth = targetAuth;

    axios.post(requestURL + "admin/changeResource", changeAuthorityDto.value ,{headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("리소스 권한 변경이 완료되었습니다.")
        })
        .catch((error) => {
            alert(error)
        })
}

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