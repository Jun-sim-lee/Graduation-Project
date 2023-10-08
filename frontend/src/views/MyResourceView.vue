<template>
    <div class = "screen">
        <div class="modal_black" v-if="showModal">
            <div class="resource_modal">
                <button style="position: absolute; left: 2px; top: 5px; border: none; background-color: transparent; 
                           color: black; font-size: 20px; font-family: 'SUITE-Regular';" @click="closeModal">X</button>
                <h3>{{ modalResourceInfo.name }}</h3>
                <img :src="modalResourceInfo.src" style="width: 150px; height: 150px;"/> 
                <p>{{ modalResourceInfo.auth }} 권한 이상의 사용자만 사용 가능합니다.</p>
                <p v-html="modalResourceInfo.info"></p>
            </div>
        </div>
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px; font-family: 'SUITE-Regular';"> &lt; 나의 리소스</button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: 'SUITE-Regular';">My Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px; font-family: 'SUITE-Regular';">직접 추가한 리소스 목록입니다.<br>
                근처에 도달하면 자원을 자유롭게 제어할 수 있습니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white; overflow: scroll;">
            <ul>
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="resource.deviceName" v-for="resource in resourceList">
                    <img v-if="resource.isOn==''" src="../assets/greenglow.png" style="width: 25px; height: 25px;">
                    <img v-if="resource.isOn!=''" src="../assets/redglow.png" style="width: 25px; height: 25px;">
                    <span style="font-size: 18px; line-height: 30px;" @click="openModal(resource.deviceName, resource.auth)">{{ resource.deviceName }}</span>
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

const resourceList = ref([
])
const modalResourceInfo = ref({
    name: "",
    auth: "",
    src: "",
    info: ""
})
const resourceInfos = new Map([
    [ "자판기", '제도관 2층에 위치해 있습니다.<br>버튼을 통해 원하는 물건을 뽑을 수 있습니다!' ],
    [ "도어락(6210)", '제도관 2층에 위치해 있습니다.<br>김XX 교수님의 연구실입니다.' ],
    [ "선풍기", "제도관 2층에 위치해 있습니다.<br>한여름 더위를 시원하게 날려버리세요!" ]
])
const showModal = ref(false)

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
            if(resp.status === 200){
                alert("리소스 삭제가 완료 되었습니다.")
                router.go(0)
            }
        })
}

function moveToPrev(){
    router.go(-1)
}

// 여기서부터는 모달 창 관련 메서드 
function openModal(targetName, targetAuth){
    showModal.value = true
    modalResourceInfo.value.name = targetName
    if(targetAuth == "Student")
        modalResourceInfo.value.auth = "학생"
    else
        modalResourceInfo.value.auth = "교수"
    modalResourceInfo.value.src = require("../assets/" + targetName + ".png")
    modalResourceInfo.value.info = resourceInfos.get(targetName)
} 

function closeModal(){
    showModal.value = false
}
</script>

<style>
@import "../style/wmistyle.css";
</style>