<template>
   <div class="flex flex-wrap align-Todos-center mb-3 gap-2">
        <InputText placeholder="First Name" id="firstName"
          v-model="formTodos.firstName" @blur="v$.firstName.$touch" />
        <div class="input-errors" v-for="error of v$.firstName.$errors" :key="error.$uid">
            <div class="error-msg">{{ error.$message }}</div>
        </div>
    </div>

    <div class="flex flex-wrap align-Todos-center mb-3 gap-2">
        <InputText placeholder="Last Name" id="lastName" 
          v-model="formTodos.lastName" @blur="v$.lastName.$touch" />
        <div class="input-errors" v-for="error of v$.lastName.$errors" :key="error.$uid">
            <div class="error-msg">{{ error.$message }}</div>
        </div>
    </div>

    <div class="flex flex-wrap align-Todos-center mb-3 gap-2">
        <InputText placeholder="Email" id="email" type="email" 
          v-model="formTodos.email" @blur="v$.email.$touch" />
        <div class="input-errors" v-for="error of v$.email.$errors" :key="error.$uid">
            <div class="error-msg">{{ error.$message }}</div>
        </div>
    </div>

    <div class="flex flex-wrap align-Todos-center mb-3 gap-2">
        <InputText placeholder="Gender" id="gender" v-model="formTodos.gender" 
          @blur="v$.gender.$touch" />
        <div class="input-errors" v-for="error of v$.gender.$errors" :key="error.$uid">
            <div class="error-msg">{{ error.$message }}</div>
        </div>
    </div>
    
</template>

<script setup>

import { defineProps, defineExpose, onMounted, reactive } from 'vue';
import { createTodo, updateTodo } from '../views/services/todoservice.js';
import { useVuelidate } from '@vuelidate/core'
import { required, email } from '@vuelidate/validators'

const props = defineProps({
    forminp: {
        firstName: '',
        lastName: '',
        email: '',
        gender: '',
        id: ''
    }
});

const formTodos = reactive({
    firstName: '',
    lastName: '',
    email: '',
    gender: ''

})

const rules = {
    firstName: { required },
    lastName: { required },
    email: { email, required },
    gender: { required }
}
const v$ = useVuelidate(rules, formTodos)


//functions
function clearForm() {
    formTodos.firstName = '';
    formTodos.lastName = '';
    formTodos.email = '';
    formTodos.gender = '';
    formTodos.id = '';
}

async function handleEditEvent() {
   
    const isFormCorrect = await v$.value.$validate();
    if (!isFormCorrect) { return false; }
    await updateTodo(formTodos);
    clearForm();
    return true;
}
async function handleAddEvent() {
  
    const isFormCorrect = await v$.value.$validate();
    if (!isFormCorrect) { return false;}
    await createTodo(formTodos);
    clearForm();
    return true;
}

defineExpose({
    handleAddEvent,
    handleEditEvent
})
onMounted(() => {
    formTodos.firstName = props.forminp.firstName;
    formTodos.lastName = props.forminp.lastName;
    formTodos.email = props.forminp.email;
    formTodos.gender = props.forminp.gender;
    formTodos.id = props.forminp.id;
})
</script>