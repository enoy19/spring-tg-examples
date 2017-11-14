package io.enoy.tg.example.actions;

import io.enoy.tg.action.SimpleTgActionProcessor;
import io.enoy.tg.action.TgActionContext;
import io.enoy.tg.action.message.TgMessageService;
import io.enoy.tg.scope.context.TgContext;
import io.enoy.tg.scope.context.TgContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.PhotoSize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

@Component
@Scope("tg")
public class SaveImageProcessor implements SimpleTgActionProcessor<SaveImageDefinition> {

    private final TgMessageService messageService;

    @Autowired
    public SaveImageProcessor(TgMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public boolean process(TgActionContext tgActionContext) {

        // I should implement a method that tests if the context is the first message (the command)
        if (tgActionContext.getInputs().size() == 1) {
            messageService.sendMessage("Please send an image.");
            return false;
        } else {
            List<PhotoSize> photos = tgActionContext.getInputs().get(1).getPhoto();
            if (Objects.isNull(photos)) {
                messageService.sendMessage("Saving image failed. Please send an image!\nTry again using /image");
                // f*ck that stupid user who couldn't manage to send an image and just end that action.
                return true;
            }

            PhotoSize largest = photos.get(photos.size() - 1);
            String path = messageService.getFilePath(largest);

            TgContext tgContext = TgContextHolder.currentContext();

            String fileName = path.substring(path.lastIndexOf('/') + 1);
            String dirName = String.format("./%d_%s", tgContext.getUser().getId(), tgContext.getUser().getUserName());

            File dir = new File(dirName);
            if(!dir.exists())
                if(!dir.mkdirs())
                    throw new IllegalStateException("Could not create directory");

            File file = new File(dir, fileName);

            try {
                saveFile(path, file);
                messageService.sendMessage("File saved!");
            } catch (IOException e) {
                messageService.sendMessage("Failed to save file. Please try again or inform the admin!");
                throw new IllegalStateException(e);
            }
        }

        return true;
    }

    private void saveFile(String path, File output) throws IOException {
        URL website = new URL(path);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(output);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    @Override
    public Class<SaveImageDefinition> getActionDefinitionClass() {
        return SaveImageDefinition.class;
    }

}
