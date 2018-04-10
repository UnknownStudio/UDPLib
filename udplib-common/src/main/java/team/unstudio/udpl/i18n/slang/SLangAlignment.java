package team.unstudio.udpl.i18n.slang;

import team.unstudio.udpl.config.EncodingDetect;
import team.unstudio.udpl.util.extra.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public interface SLangAlignment {
    static String[] align(String separator, String separatorChar, String[] list) {
        Map<Integer, String> commentList = new HashMap<>();

        // fetch comment
        for (int i = 0; i < list.length; i++) {
            commentList.put(i, list[i]);
        }

        // clean comment
        list = Arrays.stream(list).filter(it -> !it.startsWith("#")).collect(Collectors.toList()).toArray(new String[0]);

        // clean
        if (list.length < 1) return list;

        // the first line's string array
        String[] locals = list[0].split(separator);
        String[][] data = new String[list.length][locals.length];
        int[] length = new int[locals.length];
        Arrays.fill(length, 0);

        data[0] = locals;

        // init data array
        for (int i = 1; i < list.length; i++) data[i] = list[i].split(separator);

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length - 1; j++) {
                int savedLength = length[j];
                String lang = data[i][j];
                if (!lang.endsWith("∞")) lang = lang.trim();
                data[i][j] = lang;
                int nowLength = lang.length();
                if (nowLength > savedLength) length[j] = nowLength;
            }
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (length[j] != 0) data[i][j] = String.format("%1$-" + length[j] + "s", data[i][j]);
            }
        }

        List<String> builder = new ArrayList<>();
        for (String[] datum : data) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < datum.length; i++) {
                stringBuilder.append(datum[i]);
                if (i < datum.length - 1) stringBuilder.append(separatorChar);
            }
            builder.add(stringBuilder.toString());
        }

        return builder.toArray(new String[0]);
    }

    static void main(String[] args) {
        // 创建 JFrame 实例
        new AlignmentFrame();
    }

    class AlignmentFrame extends JFrame {
        public AlignmentFrame() throws HeadlessException {
            setSize(350, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();

            add(panel);

            new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
                @SuppressWarnings("unchecked")
                @Override
                public void drop(DropTargetDropEvent dtde) {
                    try {

                        if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                            List<File> list = (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));

                            String temp = "";
                            for (File file : list) {
                                String[] data = align("\\|", "|", FileUtils.readFile2Array(file, EncodingDetect.getJavaEncode(file)));
                                for (String datum : data) {
                                    System.out.println(datum);
                                }
                                FileUtils.writeArray2File(file, data, EncodingDetect.getJavaEncode(file));
                            }
                            JOptionPane.showMessageDialog(null, temp);
                            dtde.dropComplete(true);

                        } else {

                            dtde.rejectDrop();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            });

            setVisible(true);
        }
    }
}
