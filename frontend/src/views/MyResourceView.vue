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
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="resource.deviceName" v-for="resource in resourceList">
                    <img v-if="!resource.on" src="../assets/redglow.png" style="width: 25px; height: 25px;">
                    <img v-if="resource.on" src="../assets/greenglow.png" style="width: 25px; height: 25px;">
                    <span style="font-size: 18px; line-height: 30px;">{{ resource.deviceName }}</span>
                    <button @click="requestDeletion(resource.id)" style="border: none; border-radius: 30px; background-color: red; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">삭제</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted, inject} from 'vue';
import { router } from '@/router';
import axios from 'axios';

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')

const resourceList = ref([])

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestResourceList();
})

function requestResourceList(){
    axios.get(requestURL + "myResource", {headers})
        .then((resp) => {
            resourceList.value = resp.data;
        })
}

function requestDeletion(targetId){
    axios.delete(requestURL + "deleteMyResource/" + targetId, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("리소스 삭제가 완료 되었습니다.")
        })
}

function moveToPrev(){
    router.go(-1)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>