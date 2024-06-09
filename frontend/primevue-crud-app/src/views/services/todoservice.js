export async function getAllTodos() {
    // debugger
    const response = await fetch('http://localhost:8090/todos');
    return await response.json();
}

export async function createTodo(data) {
    const response = await fetch(`http://localhost:8090/todos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    return await response.json();
}

export async function updateTodo(data) {
    //TODO: Test this
    const response = await fetch(`http://localhost:8090/todos/` + data.id, {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: { 'Content-type': 'application/json' }
    })
    return await response.json();
}

export async function deleteTodo(data) {
    //debugger
    const response = await fetch('http://localhost:8090/todos/' + data.id, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
    })
    return await response.json();
}