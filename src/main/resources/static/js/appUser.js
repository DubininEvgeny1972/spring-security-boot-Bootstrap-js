$(async function () {
    await getTableWithOneUser();
    await getRolesByUser();
    await thisUser();
})

async function thisUser() {
    let userFind = $('#thisUser b');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let thisUser = user.login
            userFind.append(thisUser);
        })
}

async function getRolesByUser() {
    let roleFind = $('#RolesByUser c');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let rolesUser = user.roles.map(role => "  " + role.name)
            roleFind.append(rolesUser);
        })
}

async function getTableWithOneUser() {
    let tableUser = $('#tabUser tbody');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let tableFillingUser = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.login}</td>
                <td> ${user.roles.map(role => "  " + role.name)}</td>
            </tr>
            )`;
            tableUser.append(tableFillingUser);
        })
}
