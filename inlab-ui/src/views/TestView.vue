<template>
  <section class="mt-52">
    <!--    <teleport to="#teleport-area">-->
    <VModal>
      <template v-slot:header>
        <h2 class="mb-6 text-xl font-semibold tracking-tight">직무 접근권한</h2>
      </template>
      <template v-slot:body> </template>
      <template v-slot:footer>
        <div class="flex justify-end mt-12">
          <button
            class="flex flex-col mr-3 py-5 px-7 btn btn-sm btn-primary btn-outline"
          >
            취소
          </button>
          <button class="flex flex-col py-5 px-7 btn btn-sm btn-primary">
            확인
          </button>
        </div>
      </template>
    </VModal>
    <!--    </teleport>-->
  </section>
</template>
<script setup>
// import VSearchFilter from "@/components/teleport/SearchFilter.vue";
// import VquestionManagement from "@/components/teleport/VQuestionManagement.vue";
// import Pagination from "@/components/common/Pagination.vue";
import Pagination from "@/components/common/Pagination.vue";
</script>
