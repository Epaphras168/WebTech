# Task Management API

### Base URL: /api/tasks

- GET /api/tasks – Get all tasks
- GET /api/tasks/{taskId} – Get task by ID
- GET /api/tasks/status?completed={true/false} – Get tasks by completion status
- GET /api/tasks/priority/{priority} – Get tasks by priority
- POST /api/tasks – Create new task
- PUT /api/tasks/{taskId} – Update task
- PATCH /api/tasks/{taskId}/complete – Mark task as completed
- DELETE /api/tasks/{taskId} – Delete task
