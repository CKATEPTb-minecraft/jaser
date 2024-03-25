package dev.ckateptb.minecraft.jaser.repository.entity.synchronous.service;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import dev.ckateptb.minecraft.jaser.Jaser;
import dev.ckateptb.minecraft.jaser.repository.entity.synchronous.SynchronousEntityRepository;
import dev.ckateptb.minecraft.jyraf.container.annotation.Component;
import dev.ckateptb.minecraft.jyraf.container.annotation.PostConstruct;
import dev.ckateptb.minecraft.jyraf.repository.WorldRepositoryService;
import dev.ckateptb.minecraft.jyraf.repository.entity.listener.EntityRepositoryListener;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;

@Component
@RequiredArgsConstructor
public class SynchronousEntityRepositoryService {
    private final Jaser plugin;
    private final WorldRepositoryService service;
    private final EntityRepositoryListener listener;

    @PostConstruct
    public void init() {
        EntityAddToWorldEvent.getHandlerList().unregister(this.listener);
        EntityRemoveFromWorldEvent.getHandlerList().unregister(this.listener);
        this.service.register(this.plugin, Entity.class, SynchronousEntityRepository::new);
    }
}
