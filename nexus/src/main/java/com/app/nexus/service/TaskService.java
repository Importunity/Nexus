package com.app.nexus.service;

import com.app.nexus.exception.ResourceNotFoundException;
import com.app.nexus.information.UserPrincipal;
import com.app.nexus.model.ApplicationUser;
import com.app.nexus.model.Project;
import com.app.nexus.model.Task;
import com.app.nexus.repository.ApplicationUserRepository;
import com.app.nexus.repository.ProjectRepository;
import com.app.nexus.repository.TaskRepository;
import com.app.nexus.payload.request.TaskRequest;
import com.app.nexus.payload.response.TaskResponse;
import com.app.nexus.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author Amadeus
 */

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);


    public Task createTask(TaskRequest taskRequest, UserPrincipal currentUser, Long projectId){
        ApplicationUser applicationUser = applicationUserRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", currentUser.getId()));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setLevel(taskRequest.getLevel());
        task.setUser(applicationUser);
        task.setCompleted(taskRequest.getIs_completed());
        project.addTask(task);
        return taskRepository.save(task);
    }


    public TaskResponse getTaskById(Long taskId, UserPrincipal currentUser){
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        ApplicationUser creator = applicationUserRepository
                .findById(task.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", task.getCreatedBy()));


        return ModelMapper.mapToTaskResponse(task, creator);
    }

    public Task removeTask(Long taskId,Long projectId,UserPrincipal currentUser){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        ApplicationUser applicationUser = applicationUserRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("Current User", "id", currentUser.getId()));
        project.removeTask(task);
        applicationUser.removeTask(task);
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Long projectId, TaskRequest taskRequest){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setLevel(taskRequest.getLevel());
        task.setCompleted(taskRequest.getIs_completed());
        return taskRepository.save(task);
    }

    public List<TaskResponse> getTasksCreatedBy(String username, UserPrincipal currentUser){
        // checks to see if the username exists in the database
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Application User", "Username", username));


        Pageable wholepage = Pageable.unpaged();
        Page<Task> tasks = taskRepository.findAll(wholepage);

        List<TaskResponse> taskResponses = tasks.map((task) -> {
            return ModelMapper.mapToTaskResponse(
                    task,
                    applicationUser
            );
        }).getContent();

        return taskResponses;
    }

    public Map<Long, ApplicationUser> getTaskCreatorMap(List<Task> tasks){
        List<Long> creatorIds = tasks.stream()
                .map(Task::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());
        List<ApplicationUser> creators = applicationUserRepository.findByIdIn(creatorIds);
        Map<Long, ApplicationUser> creatorMap = creators.stream()
                .collect(Collectors.toMap(ApplicationUser::getId, Function.identity()));
        return creatorMap;
    }

}
