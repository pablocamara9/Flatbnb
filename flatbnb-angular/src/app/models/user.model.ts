export interface User {
    id: string
    username: string
    token: string
    refreshToken: string
}

export interface UserRegister {
    token: string
}