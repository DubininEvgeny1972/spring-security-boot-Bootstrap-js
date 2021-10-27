$(async function () {
    await getTableWithUsers();

    await getTableWithAdmin();
    await getRolesByUser();
    await thisUser();

    getNewUserForm();
    getDefaultModal();
    addNewUser();
})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    // bodyAdd : async function(user) {return {'method': 'POST', 'headers': this.head, 'body': user}},
    findAllUsers: async () => await fetch('users'),
    findOneUser: async (id) => await fetch(`${id}`),
    addNewUser: async (user) => await fetch('add', {method: 'POST', headers: userFetchService.head, body: JSON.stringify(user)}),
    updateUser: async (user, id) => await fetch(`${id}`, {method: 'PUT', headers: userFetchService.head, body: JSON.stringify(user)}),
    deleteUser: async (id) => await fetch(`${id}`, {method: 'DELETE', headers: userFetchService.head})
}
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

async function getTableWithAdmin() {
    let table = $('#mainTableWithAdmin tbody');
    fetch('user')
        .then(res => res.json())
        .then(user => {
            let tableFilling = `$(
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.login}</td>
                <td> ${user.roles.map(role => "  " + role.name)}</td>
            </tr>
            )`;
            table.append(tableFilling);
        })
}

async function getTableWithUsers() {
    let table = $('#mainTableWithUsers tbody');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.login}</td>
                        <td> ${user.roles.map(role => "  " + role.name)}</td>
                        <td>
                            <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info" 
                            data-toggle="modal" data-target="#someDefaultModal">Edit</button>
                        </td>
                        <td>
                             <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger" 
                             data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                        </td>
                    </tr>
                )`;
                table.append(tableFilling);
            })
        })

    // обрабатываем нажатие на любую из кнопок edit или delete
    // достаем из нее данные и отдаем модалке, которую к тому же открываем
    $("#mainTableWithUsers").find('button').on('click', (event) => {
        let defaultModal = $('#someDefaultModal');

        let targetButton = $(event.target);
        let buttonUserId = targetButton.attr('data-userid');
        let buttonAction = targetButton.attr('data-action');

        defaultModal.attr('data-userid', buttonUserId);
        defaultModal.attr('data-action', buttonAction);
        defaultModal.modal('show');
    })
}



// что то деалем при открытии модалки и при закрытии
// основываясь на ее дата атрибутах
async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');
        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {
        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}


// редактируем юзера из модалки редактирования, забираем данные, отправляем
async function editUser(modal, id) {
    let preuser = await userFetchService.findOneUser(id);
    let user = preuser.json();
    let allEditRoles = []
    await fetch('roles')
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                allEditRoles.push(role)
            })
        })

    modal.find('.modal-title').html('Edit user');

    let editButton = `<button  class="btn btn-outline-success" id="editButton">Edit</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(editButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="editUser">
                <label for="id" class="font-weight-bold">ID<input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled>
                <label th:for="name" class="font-weight-bold">First Name<input class="form-control" type="text" id="name" value="${user.name}">
                <label th:for="lastName" class="font-weight-bold">Last Name<input class="form-control" type="text" id="lastName" value="${user.lastName}">
                <label th:for="age" class="font-weight-bold">Age<input class="form-control" id="age" type="number" value="${user.age}">
                <label th:for="login" class="font-weight-bold">Login<input class="form-control" type="text" id="login" value="${user.login}">
                <label th:for="password" class="font-weight-bold">Password<input class="form-control" type="password" id="password">
                <h1></h1>
                <label th:for="password" class="font-weight-bold">Role<br>
                <select class="form-control" id="mySelectId" name="mySelect" multiple size="2">
                    <option value="${allEditRoles[0].name}">${allEditRoles[0].name}</option>
                    <option value="${allEditRoles[1].name}">${allEditRoles[1].name}</option>
                </select>
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    });


    $("#editButton").on('click', async () => {
        let id = modal.find("#id").val().trim();
        let age = modal.find("#age").val().trim();
        let name = modal.find("#name").val().trim();
        let lastName = modal.find("#lastName").val().trim();
        let login = modal.find("#login").val().trim();
        let password = modal.find("#password").val().trim();
        let roleByUserEdit = [];

        var elEditRole = document.getElementById("mySelectId");
        for (var i = 0; i < elEditRole.options.length; i++) {
            var oneAddRole = elEditRole.options[i];
            if (oneAddRole.selected) roleByUserEdit.push(oneAddRole.value);
        }

        let data = {
            id: id,
            name: name,
            lastName: lastName,
            login: login,
            password: password,
            age: age,
            roles: roleByUserEdit
        }
        const response = await userFetchService.updateUser(data, id);
        getTableWithUsers();
        modal.modal('hide');
    })
}

async function deleteUser(modal, id) {
    let preuser = await userFetchService.findOneUser(id);
    let user = preuser.json();

    modal.find('.modal-title').html('Delete user');

    let deleteButton = `<button  class="btn btn-danger" id="deleteButton">Delete</button>`;
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
    modal.find('.modal-footer').append(deleteButton);
    modal.find('.modal-footer').append(closeButton);

    user.then(user => {
        let bodyForm = `
            <form class="form-group text-center" id="editUser">
                <label for="id" class="font-weight-bold">ID<input type="text" class="form-control" id="id" name="id" value="${user.id}" disabled><br>
                <label th:for="name" class="font-weight-bold">First Name<input class="form-control" type="text" id="name" value="${user.name}" disabled><br>
                <label th:for="lastName" class="font-weight-bold">Last Name<input class="form-control" type="text" id="lastName" value="${user.lastName}" disabled><br>
                <label th:for="age" class="font-weight-bold">Age<input class="form-control" id="age" type="number" value="${user.age}" disabled>
                <label th:for="login" class="font-weight-bold">Login<input class="form-control" type="text" id="login" value="${user.login}" disabled><br>               
            </form>
        `;
        modal.find('.modal-body').append(bodyForm);
    })
    $("#deleteButton").on('click', async () => {
        const response = await userFetchService.deleteUser(id);
        getTableWithUsers();
        modal.modal('hide');
    })
}

async function getNewUserForm() {
    let allAddRoles = []
    await fetch('roles')
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                allAddRoles.push(role)
            })
        })
    let select = $('#defaultSomeForm div');
    let bodyFilling = `
        <select class="form-control" id="mySelectForAddId" name="mySelect" multiple size="2">
            <option value="${allAddRoles[0].name}">${allAddRoles[0].name}</option>
            <option value="${allAddRoles[1].name}">${allAddRoles[1].name}</option>
        </select>
    `;
    select.append(bodyFilling);
    $('#addNewUserButton').click(async () =>  {
        let addUserForm = $('#defaultSomeForm')
        let name = addUserForm.find('#AddNewUserName').val().trim();
        let lastName = addUserForm.find('#AddNewUserLastName').val().trim();
        let age = addUserForm.find('#AddNewUserAge').val().trim();
        let login = addUserForm.find('#AddNewUserLogin').val().trim();
        let password = addUserForm.find('#AddNewUserPassword').val().trim();
        let roleByUserAdd = [];

        var elAddRole = document.getElementById("mySelectForAddId");
        for (var i = 0; i < elAddRole.options.length; i++) {
            var oneAddRole = elAddRole.options[i];
            if (oneAddRole.selected) roleByUserAdd.push(oneAddRole.value);
        }

        let data = {
            name: name,
            lastName: lastName,
            age: age,
            login: login,
            password: password,
            roles: roleByUserAdd
        }
        const response = await userFetchService.addNewUser(data);
        getTableWithUsers();
        addUserForm.find('#AddNewUserName').val('');
        addUserForm.find('#AddNewUserLastName').val('');
        addUserForm.find('#AddNewUserAge').val('');
        addUserForm.find('#AddNewUserLogin').val('');
        addUserForm.find('#AddNewUserPassword').val('');
    })
}
