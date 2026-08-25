import axiosInstance from "./config/AxiosInstance.ts";
import {Character, Todo} from "./types/types.ts";

export const getCharacters = async (): Promise<Character[]> => {
  const apiResponse = await axiosInstance.get("api/character/all");
  return apiResponse.data;
}

export const getTodos = async (): Promise<Todo[]> => {
  const apiResponse = await axiosInstance.get("/api/todo/all");
  return apiResponse.data;
}

export const createTodo = async (todo: Todo): Promise<Todo> => {
  const apiResponse = await axiosInstance.post("/api/todo/create", todo)
  return apiResponse.data;
}

export const toggleTodo = async (id: number): Promise<Todo> => {
  const apiResponse = await axiosInstance.post("/api/todo/toggle", JSON.stringify(id), {
    headers: {
      'Content-Type': 'application/json'
    }
  })
  return apiResponse.data;
}