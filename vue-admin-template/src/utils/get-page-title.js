import defaultSettings from '@/settings'

const title = defaultSettings.title || '宠物烘焙零食销售平台'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
