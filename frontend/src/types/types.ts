export type Character = {
  id: number;
  name: string;
  age: number;
  sex: string;
  species: string;
  position: number;
}

export type Todo = {
  id?: number;
  task: string;
  done?: boolean;
}