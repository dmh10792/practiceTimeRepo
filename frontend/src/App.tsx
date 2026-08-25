import {useEffect, useState} from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './App.css'
import {Todo} from "./types/types.ts";
import {createTodo, getTodos, toggleTodo} from "./Client.ts";

function App() {
  // @ts-ignore
  const [count, setCount] = useState(0)
  const [todos, setTodos] = useState<Todo[]>([])

  const loadTodos = () => {
    getTodos().then((data) => {
      setTodos(data);
    })
    console.log(todos);
  }

  useEffect(() => {
    loadTodos();
  }, [])

  const [formData, setFormData] = useState<Todo>({
    task: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    createTodo(formData).then((data) => {
      console.log('Created todo:', data);
      loadTodos();
      setFormData({
        task: ''
      })
    })
  };

  const clickCheckbox = (id: number) => {
    console.log("clicked todo", id)
    toggleTodo(id).then((data) => {
      console.log('Toggled todo:', data);
      loadTodos();
    })
  };

  return (
    <>
      <h1>Platform Practice Time</h1>
      <table style={{width: "100%", borderCollapse: "collapse"}}>
        <thead>
        <tr style={{borderBottom: "2px solid #ccc", textAlign: "left"}}>
          <th>Task</th>
          <th>Done</th>
        </tr>
        </thead>
        <tbody>
        {todos.map((todo) => (
          <tr key={todo.id} style={{borderBottom: "1px solid #eee", textAlign: "left"}}>
            <td style={{ textDecoration: todo.done ? 'line-through' : 'none' }}>{todo.task}</td>
            <td>
              <input
                type="checkbox"
                checked={todo.done}
                onChange={() => clickCheckbox(todo.id!)}
              />
            </td>
          </tr>
        ))}
        </tbody>
      </table>
      <br/>
      <div className={"todo-form"}>
        <form onSubmit={handleSubmit}
              style={{display: 'flex', flexDirection: 'column', gap: '10px', maxWidth: '300px'}}>
          <div>
            <label htmlFor="task">New Task: </label>
            <input
              type="text"
              id="task"
              name="task"
              value={formData.task}
              onChange={handleChange}
            />
          </div>
          <br/>
          <button type="submit">Add Task</button>
        </form>
      </div>
      {/* Button goes here. */}
    </>
  )
}

export default App
