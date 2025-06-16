export interface User {
    id: string
    username: string
    token: string
    refreshToken: string
    role: string
}

export interface Root {
  listadoUsuarios: ListadoUsuario[]
}

export interface ListadoUsuario {
  id: string
  username: string
  nombre: string
  apellidos: string
  email: string
  telefono: string
  role: string
  enabled: boolean
  createdAt: string
}

export interface UserRegister {
    token: string
}