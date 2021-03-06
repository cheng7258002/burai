/*
 * Copyright (C) 2016 Satomichi Nishihara
 *
 * This file is distributed under the terms of the
 * GNU General Public License. See the file `LICENSE'
 * in the root directory of the present distribution,
 * or http://www.gnu.org/copyleft/gpl.txt .
 */

package burai.app.project.viewer.result.graph;

import java.io.File;
import java.io.IOException;

import burai.app.project.QEFXProjectController;
import burai.app.project.editor.result.graph.QEFXGraphEditor;
import burai.app.project.viewer.result.QEFXResultButton;

public abstract class QEFXGraphButton<V extends QEFXGraphViewer<?>> extends QEFXResultButton<V, QEFXGraphEditor> {

    private File propertyFile;

    protected QEFXGraphButton(QEFXProjectController projectController, String title, String subTitle) {
        super(projectController, title, subTitle);
        this.propertyFile = null;
    }

    protected void setPropertyFile(File propertyFile) {
        this.propertyFile = propertyFile;
    }

    protected abstract V createGraphViewer() throws IOException;

    @Override
    protected final V createResultViewer() throws IOException {
        V viewer = this.createGraphViewer();
        if (viewer != null) {
            QEFXGraphViewerController controller = viewer.getController();
            if (controller != null) {
                controller.setPropertyFile(this.propertyFile);
            }
        }

        return viewer;
    }

    @Override
    protected final QEFXGraphEditor createResultEditor(V resultViewer) throws IOException {
        if (resultViewer == null) {
            return null;
        }

        if (this.projectController == null) {
            return null;
        }

        return new QEFXGraphEditor(this.projectController, resultViewer);
    }
}
